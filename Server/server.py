from socket import *
import threading
import sys
import io
import os
from pdf2image import convert_from_path
import cv2 # 이미지 전처리를 위한 openCV 라이브러리
from google.cloud import vision # OCR을 위한 google vision api
from hanspell import spell_checker # 맞춤법 검사 라이브러리
import dict
from pdf4me.client.pdf4me_client import Pdf4meClient
from pdf4me.client.convert_client import ConvertClient
import olefile

# OCR 결과 텍스트 후처리 함수
dic=dict.dic
def checkDict(line):
    for word in dic:
        if word in line:
            line.replace(word,dic[word])
    return line

def convertPdfToImage(file_path):
    try:
        pages = convert_from_path(file_path)
        res = ''
        for i, page in enumerate(pages):
            page.save('./file.jpg', 'JPEG')
            res += runOCR('jpg')
        return res
    except Exception as e:
        return '파일이 손상되었거나 제한된 글꼴이 포함되어 있습니다.'

# OCR 수행 함수
# 전처리, OCR, 후처리
def runOCR(data_type):
    #Image Preprocessing
    road_img=cv2.imread("file." + data_type)
    #이미지 흑백화
    gray_img=cv2.cvtColor(road_img,cv2.COLOR_BGR2GRAY)
    #이미지 이진화
    res_img=cv2.adaptiveThreshold(gray_img,255,cv2.ADAPTIVE_THRESH_GAUSSIAN_C,cv2.THRESH_BINARY,blockSize=31,C=5)
    #이미지 잡티제거
    res_img=cv2.bilateralFilter(res_img,9,75,75)
    #이미지 저장
    cv2.imwrite('processed_img.jpg', res_img)

    #OCR(Google Cloud Vision API)
    # Instantiates a client
    client = vision.ImageAnnotatorClient()
    # The name of the image file to annotate
    file_name = os.path.abspath('file.' + data_type)
    # Loads the image into memory
    with io.open(file_name, 'rb') as image_file:
        content = image_file.read()
    image = vision.Image(content=content)
    # Performs label detection on the image file
    response = client.text_detection(image=image)
    texts = response.text_annotations

    tmp=[]
    for text in texts:
        tmp.append(text.description)
        #print(text.description)
    if len(tmp)==0:
        tmp.append("첨부하신 파일에 텍스트가 존재하지 않습니다.")
    res=''
    for i in tmp[0].split('\n'):
        line=''
        for j in i.split(' '):
            line+=j+' '
        # line=checkDict(line)
        # res+=spell_checker.check(line).checked+'\n'
        res += line + '\n'
    return res

# 다중접속 처리를 위한 스레딩 함수
def handleReceive(connection_socket, addr_info):
    res = '' # ocr결과 텍스트
    try:

        # 연결 수락
        connection_socket.settimeout(10)
        print('accept!')
        print('--client information--')
        print(connection_socket)

        # 클라이언트로부터 데이터를 가져옴
        print("receiving...")
        while True:
            img_data = connection_socket.recv(BUFSIZE)
            data = img_data
            if img_data:
                while img_data:
                    img_data = connection_socket.recv(BUFSIZE)
                    data += img_data
            else:
                break

        # 에러가 발생한 경우
        # 정상적으로 동작할 경우 위에 설정한 timeout으로 인하여 밑의 except 구문으로 넘어가야함
        connection_socket.send(bytes(data,'utf-8'))
        connection_socket.close()
        print('error!')
        print('connection closed')

    except Exception as e:

        print("finish recv!")

        # 데이터 타입 구하기
        data_type = data.split(':type'.encode())[0].decode()
        file_data = data.split(':type'.encode())[1]
        print('date type: ', data_type)

        # 받은 데이터를 파일로 저장
        receive_file = open("./file." + data_type, 'wb')
        receive_file.write(file_data)
        receive_file.close()

        # 받은 데이터의 형식이 pdf이면 이미지로 변환
        if(data_type == 'pdf'):
            res = convertPdfToImage('./file.pdf')
        elif(data_type == 'pptx' or data_type == 'ppt' or data_type == 'doc' or data_type == 'docx'): #ppt, word의 경우 pdf로 변환 후 이미지로 변환
            # init client
            pdf4me_client = Pdf4meClient(token='OGFmNmFmYzQtMGZlYS00NmFhLTkwNGYtNTRiNmM0MGIxN2ZkOk1lT2FPZjN6b3NhbnNnQnY3TFVEeThQRWtpaVA5d00l')
            convert_client = ConvertClient(pdf4me_client)

            # conversion
            generated_pdf = convert_client.convert_file_to_pdf(
                file_name='file.' + data_type,
                file=open("./file." + data_type, 'rb')
            )
            # writing the generated PDF to disk
            with open('file.pdf', 'wb') as f:
                f.write(generated_pdf)
            res = convertPdfToImage('./file.pdf')    
        elif(data_type == 'png' or data_type == 'jpg' or data_type == 'jpeg'):  # 이미지 파일의 경우
            res = runOCR(data_type)
        elif(data_type == 'hwp'):
            hwp_file = olefile.OleFileIO('file.hwp')
            encoded_text = hwp_file.openstream('PrvText').read()
            decoded_text = encoded_text.decode('UTF-16')
            res = decoded_text
        else:  # 지원하지 않는 형식의 경우
            res = '지원하지 않는 파일 형식입니다.'

        # 결과 출력 및 클라이언트에게 결과 송신
        print('\nTexts:')
        print(res)
        connection_socket.send(bytes(res,'utf-8'))
        connection_socket.close()
        print('connection closed')

# 소켓 정보 초기화
HOST = ''
PORT = 4041
BUFSIZE = 1024
ADDR = (HOST, PORT)
CLIENT_NUM = 10  # 연결 가능한 클라이언트 수
 
# 소켓 생성
serverSocket = socket(AF_INET, SOCK_STREAM)
 
# 소켓 주소 정보 할당
serverSocket.bind(ADDR)
print('bind')

# 연결 수신 대기 상태
serverSocket.listen(CLIENT_NUM)
print('listen')

while True:
    print('waiting...')

    # 연결 수락
    connection_socket, addr_info = serverSocket.accept()

    # 다중 접속을 위해 OCR 전체 과정을 스레드로 동작
    thread = threading.Thread(target=handleReceive, args=(connection_socket, addr_info))
    thread.daemon = True
    thread.start()