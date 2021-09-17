#include <SoftwareSerial.h> //시리얼통신 라이브러리 호출

// 블루투스에 사용하는 SERIAL
SoftwareSerial BT_SERIAL(2 , 3);

// 점자표시기 세팅
#include "braille.h"
int dataPin[3] = {4, 7, 10}; // DATA 핀번호
int latchPin[3] = {5, 8, 11}; // LATCH 핀번호
int clockPin[3] = {6, 9, 12}; // CLOCK 핀번호
int no_module = 3; // 점자 출력기 연결 개수
braille bra = braille(dataPin[0], latchPin[0], clockPin[0], no_module);
braille bra2 = braille(dataPin[1], latchPin[1], clockPin[1], no_module);
braille bra3 = braille(dataPin[2], latchPin[2], clockPin[2], no_module);
int flag = 0;
int yellow_led = 13;

char string_buffer[100]; // 블루투스로 부터 수신 받은 문자열
char string_buffer_serial[100][4]; // 수신 받은 문자열을 글자 단위로 분리하여 배열에 저장

int text_width = 0; // 문자열의 그래픽 픽셀 수
int str_char_count = 0; // 전체 문자개수

char * cho_han_one[19] = {
  "ㄱ", "ㄲ", "ㄴ", "ㄷ", "ㄸ", "ㄹ", "ㅁ", "ㅂ", "ㅃ", "ㅅ", "ㅆ", "ㅇ", "ㅈ", "ㅉ", "ㅊ", "ㅋ", "ㅌ", "ㅍ", "ㅎ"
};

byte hangul_cho[19] =
{
  0b00010000,//ㄱ
  0b00010000,//ㄲ
  0b00110000,//ㄴ
  0b00011000,//ㄷ
  0b00011000,//ㄸ
  0b00000100,//ㄹ
  0b00100100,//ㅁ
  0b00010100,//ㅂ
  0b00010100,//ㅃ
  0b00000001,//ㅅ
  0b00000001,//ㅆ
  0b00111100,//o
  0b00010001,//ㅈ
  0b00010001,//ㅉ
  0b00000101,//ㅊ
  0b00111000,//ㅋ
  0b00101100,//ㅌ
  0b00110100,//ㅍ
  0b00011100 //ㅎ
}; // 점자 표시 데이타
// 초성의 코드 번호
// ㄱ,ㄲ,ㄴ,ㄷ,ㄸ,ㄹ,ㅁ,ㅂ,ㅃ,ㅅ,ㅆ,ㅇ,ㅈ,ㅉ,ㅊ,ㅋ,ㅌ,ㅍ,ㅎ
byte hangul2_cho[19] = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18};

char * coll_han_one[21] = {
  "ㅏ", "ㅐ", "ㅏ", "ㅒ", "ㅓ", "ㅔ", "ㅕ", "ㅖ", "ㅗ", "ㅘ", "ㅙ", "ㅚ", "ㅛ", "ㅜ", "ㅝ", "ㅞ", "ㅟ", "ㅠ", "ㅡ", "ㅢ", "ㅣ"
};

byte hangul_jung[21] =
{
  0b00101001, // ㅏ
  0b00101110, // ㅐ
  0b00010110, // ㅑ
  0b00010110, // ㅒ
  0b00011010, // ㅓ
  0b00110110, // ㅔ
  0b00100101, // ㅕ
  0b00010010, // ㅖ
  0b00100011, // ㅗ
  0b00101011, // ㅘ
  0b00101011, // ㅙ
  0b00110111, // ㅚ
  0b00010011, // ㅛ
  0b00110010, // ㅜ
  0b00111010, // ㅝ
  0b00111010, // ㅞ
  0b00110010, // ㅟ
  0b00110001, // ㅠ
  0b00011001, // ㅡ
  0b00011101, // ㅢ
  0b00100110 // ㅣ
};

byte hangul2_jung[21] = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20};

byte hangul_jong[28] =
{
  0b00000000, // 없음
  0b00100000, // ㄱ
  0b00100000, // ㄲ
  0b00100000, // ㄳ
  0b00001100, // ㄴ
  0b00001100, // ㄵ
  0b00001100, // ㄶ
  0b00000110, // ㄷ
  0b00001000, // ㄹ
  0b00001000, // ㄺ
  0b00001000, // ㄻ
  0b00001000, // ㄼ
  0b00001000, // ㄽ
  0b00001000, // ㄾ
  0b00001000, // ㄿ
  0b00001000, // ㅀ
  0b00001001, // ㅁ
  0b00101000, // ㅂ
  0b00101000, // ㅄ
  0b00000010, // ㅅ
  0b00000010, // ㅆ
  0b00001111, // ㅇ
  0b00100010, // ㅈ
  0b00001010, // ㅊ
  0b00001110, // ㅋ
  0b00001011, // ㅌ
  0b00001101, // ㅍ
  0b00000111 // ㅎ
};
byte hangul2_jong[28] = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27};

byte ascii_data[127] =
{
  0b00000000, // 0
  0b00000000, // 1
  0b00000000, // 2
  0b00000000, // 3
  0b00000000, // 4
  0b00000000, // 5
  0b00000000, // 6
  0b00000000, // 7
  0b00000000, // 8
  0b00000000, // 9
  0b00000000, // 10
  0b00000000, // 11
  0b00000000, // 12
  0b00000000, // 13
  0b00000000, // 14
  0b00000000, // 15
  0b00000000, // 16
  0b00000000, // 17
  0b00000000, // 18
  0b00000000, // 19
  0b00000000, // 20
  0b00000000, // 21
  0b00000000, // 22
  0b00000000, // 23
  0b00000000, // 24
  0b00000000, // 25
  0b00000000, // 26
  0b00000000, // 27
  0b00000000, // 28
  0b00000000, // 29
  0b00000000, // 30
  0b00000000, // 31
  0b00000000, // 32 SPACE
  0b00001110, // 33 !
  0b00001011, // 34 "
  0b00000000, // 35 #
  0b00000000, // 36 $
  0b00010010, // 37 %
  0b00000000, // 38 &
  0b00000000, // 39 '
  0b00001011, // 40 (
  0b00000001, // 41 )
  0b00100001, // 42 *
  0b00001001, // 43 +
  0b00000100, // 44 ,
  0b00000110, // 45 -
  0b00001101, // 46 .
  0b00010101, // 47 /
  0b00011100, // 48 0
  0b00100000, // 49 1
  0b00101000, // 50 2
  0b00110000, // 51 3
  0b00110100, // 52 4
  0b00100100, // 53 5
  0b00111000, // 54 6
  0b00111100, // 55 7
  0b00101100, // 56 8
  0b00011000, // 57 9
  0b00000100, // 58 :
  0b00000101, // 59 ;
  0b00000100, // 60 <
  0b00001100, // 61 =
  0b00000111, // 62 >
  0b00001011, // 63 ?
  0b00000000, // 64 @
  0b00100000, // 65 A
  0b00101000, // 66 B
  0b00110000, // 67 C
  0b00110100, // 68 D
  0b00100100, // 69 E
  0b00111000, // 70 F
  0b00111100, // 71 G
  0b00101100, // 72 H
  0b00011000, // 73 I
  0b00011100, // 74 J
  0b00100010, // 75 K
  0b00101010, // 76 L
  0b00110010, // 77 M
  0b00110110, // 78 N
  0b00100110, // 79 O
  0b00111010, // 80 P
  0b00111110, // 81 Q
  0b00101110, // 82 R
  0b00011010, // 83 S
  0b00011110, // 84 T
  0b00100011, // 85 U
  0b00101011, // 86 V
  0b00011101, // 87 W
  0b00110011, // 88 X
  0b00110111, // 89 Y
  0b00100111, // 90 Z
  0b00001011, // 91 [
  0b00010000, // 92 \
  0b00000101, // 93 ]
  0b00000000, // 94 ^
  0b00000011, // 95 _
  0b00000000, // 96 '
  0b00100000, // 97 a
  0b00101000, // 98 b
  0b00110000, // 99 c
  0b00110100, // 100 d
  0b00100100, // 101 e
  0b00111000, // 102 f
  0b00111100, // 103 g
  0b00101100, // 104 h
  0b00011000, // 105 i
  0b00011100, // 106 j
  0b00100010, // 107 k
  0b00101010, // 108 l
  0b00110010, // 109 m
  0b00110110, // 110 n
  0b00100110, // 111 o
  0b00111010, // 112 p
  0b00111110, // 113 q
  0b00101110, // 114 r
  0b00011010, // 115 s
  0b00011110, // 116 t
  0b00100011, // 117 u
  0b00101011, // 118 v
  0b00011101, // 119 w
  0b00110011, // 120 x
  0b00110111, // 121 y
  0b00100111, // 122 z
  0b00001011, // 123 {
  0b00000000, // 124 |
  0b00000100, // 125 }
  0b00000000, // 126 ~
};

void setup()
{
  Serial.begin(9600);
  pinMode(yellow_led, OUTPUT);
  BT_SERIAL.begin(9600);
  bra.begin();
  bra2.begin();
  bra3.begin();
  bra.all_off();
  bra.refresh();
  bra2.all_off();
  bra2.refresh();
  bra3.all_off();
  bra3.refresh();
}

void loop()
{
  digitalWrite(yellow_led, LOW);
  if ( BT_SERIAL.available() ) // 블루투스로 신호를 받은 경우
  {
    Serial.println("전송받음");
    String str = BT_SERIAL.readStringUntil('\n'); // 문자열을 줄바꿈 끝까지 수신 받음
    //String str = "전송받음";
    Serial.println(str);
    str.replace("\r", ""); // 문자의 종료를 알리는 \r을 제거
    strcpy(string_buffer, str.c_str()); // 정리된 문자열을 string_buffer에 저장

    // 입력 받은 글자 각 배열에 할당
    // 입력 받은 문자열이 UTF8이므로 문자를 한글의 경우 초성, 중성 ,종성으로 분리해야함

    int ind = 0; // 처리중인 문자열 BYTE 위치
    int len = strlen(string_buffer); // 문자열 BYTE 수
    int index = 0; // 문자의 현재 처리 개수
    text_width = 0;
    while ( ind < len ) // 처리가 완료될때까지
    {
      int bytes = get_char_byte(string_buffer + ind); // 해당 위치의 첫번째 바이트를 읽음
      if ( bytes == 1 ) // 영문인경우
      {
        string_buffer_serial[index][0] = *(string_buffer + ind);
        string_buffer_serial[index][1] = 0;
        string_buffer_serial[index][2] = 0;
        string_buffer_serial[index][3] = 0;
        index++;
        text_width += 16;
      }
      else if ( bytes == 3 ) // 한글인 경우
      {
        string_buffer_serial[index][0] = *(string_buffer + ind);
        string_buffer_serial[index][1] = *(string_buffer + ind + 1);
        string_buffer_serial[index][2] = *(string_buffer + ind + 2);
        string_buffer_serial[index][3] = 0;
        index++;
        text_width += 16;
      }
      ind += bytes;
    }
    str_char_count = index;


    ind = 0;
    for ( int i = 0; i < str_char_count; i++) // 전체 문자에 대해서 처리
    {
      Serial.println((char*)(string_buffer_serial + ind));
      Serial.println("문자열 출력");
      // 입력 받은 문자열을 영문 혹은 초성 중성 종성으로 분리
      if ( string_buffer_serial[i][1] == 0 ) // ascii 코드인 경우
      {
        digitalWrite(yellow_led, HIGH);
        int code = string_buffer_serial[i][0];
        bra.all_off();
        ascii_braille(code, flag);
        delay(3000);
        bra.all_off();
        bra.refresh();
        bra2.all_off();
        bra2.refresh();
        bra3.all_off();
        bra3.refresh();
        delay(200);
        Serial.println(ascii_data[code], BIN);
        ind = ind + 1;
      }
      else
        // 한글인 경우
      {
        unsigned int cho_ind, jung_ind;
        char *isCo = (char *)(string_buffer_serial + ind);
        if (only_consonant(isCo, cho_ind) == 0 && only_collection(isCo, jung_ind) == 0) {
          Serial.println("다 있다");
          digitalWrite(yellow_led, LOW);
          unsigned int cho, jung, jong;
          // 한글을 초성 중성 종성으로 분리
          split_han_cho_jung_jong(string_buffer_serial[i][0], string_buffer_serial[i][1], string_buffer_serial[i][2], cho, jung, jong);
          bra.all_off();
          // 분리된 초성 중성 종성을 점자 표시기에 출력
          han_braille(cho, jung, jong, flag);
          delay(3000);
          bra.all_off();
          bra.refresh();
          bra2.all_off();
          bra2.refresh();
          bra3.all_off();
          bra3.refresh();
          delay(200);
          Serial.print(hangul_cho[cho], BIN);
          Serial.print(",");
          Serial.print(hangul_jung[jung], BIN);
          Serial.print(",");
          Serial.print(hangul_jong[jong], BIN);
          Serial.print("\n");
          ind = ind + 1;
        } //자음만 입력될 경우
        else if (only_consonant(isCo, cho_ind) == 1) {
          digitalWrite(yellow_led, LOW);
          Serial.println("자음만");
          bra.all_off();
          consonant_print(cho_ind, flag);
          delay(3000);
          bra.all_off();
          bra.refresh();
          bra2.all_off();
          bra2.refresh();
          bra3.all_off();
          bra3.refresh();
          delay(200);
          Serial.print((char*)(string_buffer_serial + ind));
          ind = ind + 1;
        } //모음만 입력될 경우
        else if (only_collection(isCo, jung_ind) == 1) {
          digitalWrite(yellow_led, LOW);
          Serial.println("모음만");
          bra.all_off();
          collection_print(jung_ind, flag);
          delay(3000);
          bra.all_off();
          bra.refresh();
          bra2.all_off();
          bra2.refresh();
          bra3.all_off();
          bra3.refresh();
          delay(200);
          Serial.print((char*)(string_buffer_serial + ind));
          ind = ind + 1;
        }
      }
      if (flag == 2) flag = 0;
      else flag = flag + 1;
    }
    Serial.println("###");
    digitalWrite(yellow_led, LOW);
    BT_SERIAL.flush();
  }

}

// UTF-8 문자열에서 한글자의 BYTE수를 구하는 함수
unsigned char get_char_byte(char *pos)
{
  char val = *pos;
#ifdef DEBUG
  Serial.println("####");
  Serial.println(val, BIN);
  Serial.println("####");
#endif
  if ( ( val & 0b10000000 ) == 0 )
  {
    return 1;
  }
  else if ( ( val & 0b11100000 ) == 0b11000000 )
  {
    return 2;
  }
  else if ( ( val & 0b11110000 ) == 0b11100000 )
  {
    return 3;
  }
  else if ( ( val & 0b11111000 ) == 0b11110000 )
  {
    return 4;
  }
  else if ( ( val & 0b11111100 ) == 0b11111000 )
  {
    return 5;
  }
  else
  {
    return 6;
  }
}

//초성 하나만 입력했는지 확인하는 함수
//초성만 있으면 1을 반환, 아니면 0을 반환한다.
int only_consonant(char *con, unsigned int & index) {
  for ( int i = 0; i < 19; i++)
  {
    if (strcmp(con, cho_han_one[i]) == 0)
    {
      index = i;
      return 1;
    }
  }
  return 0;
}
//초성 하나만 입력됐을 때, 점자로 출력하는 함수이다.
void consonant_print(int index, int mod_num) {
  bra.all_off();
  for ( int i = 0; i < 6; i++)
  {
    int on_off = hangul_cho[index] >> (5 - i) & 0b00000001; // 초성의 점자 데이타의 i번째 비트를 가져옴
    if ( on_off != 0 )
    {
      if (flag == 0) bra.on(0, i);
      if (flag == 1) bra2.on(0, i);
      if (flag == 2) bra3.on(0, i);
    }
    else
    {
      if (flag == 0) bra.off(0, i);
      if (flag == 1) bra2.off(0, i);
      if (flag == 2) bra3.off(0, i);
    }
  }
  bra.refresh();
  bra2.refresh();
  bra3.refresh();
}

//모음 하나만 입력했는지 확인하는 함수
//모음만 있으면 1을 반환, 아니면 0을 반환한다.
int only_collection(char *col, unsigned int & index) {
  for ( int i = 0; i < 21; i++)
  {
    if (strcmp(col, coll_han_one[i]) == 0)
    {
      index = i;
      return 1;
    }
  }
  return 0;
}

//모음 하나만 입력됐을 때, 점자로 출력하는 함수이다.
void collection_print(int index, int mod_num) {
  bra.all_off();
  for ( int i = 0; i < 6; i++)
  {
    int on_off = hangul_jung[index] >> (5 - i) & 0b00000001; // 초성의 점자 데이타의 i번째 비트를 가져옴
    if ( on_off != 0 )
    {
      if (flag == 0) bra.on(0, i);
      if (flag == 1) bra2.on(0, i);
      if (flag == 2) bra3.on(0, i);
    }
    else
    {
      if (flag == 0) bra.off(0, i);
      if (flag == 1) bra2.off(0, i);
      if (flag == 2) bra3.off(0, i);
    }
  }
  bra.refresh();
  bra2.refresh();
  bra3.refresh();
}


void ascii_braille(int code, int mod_num)
{
  bra.all_off();
  for ( int i = 0; i < 6; i++)
  {
    int on_off = ascii_data[code] >> (5 - i) & 0b00000001; // 초성의 점자 데이타의 i번째 비트를 가져옴
    if ( on_off != 0 )
    {
      if (flag == 0) bra.on(0, i);
      if (flag == 1) bra2.on(0, i);
      if (flag == 2) bra3.on(0, i);
    }
    else
    {
      if (flag == 0) bra.off(0, i);
      if (flag == 1) bra2.off(0, i);
      if (flag == 2) bra3.off(0, i);
    }
  }
  bra.refresh();
  bra2.refresh();
  bra3.refresh();
}

void split_han_cho_jung_jong(char byte1, char byte2, char byte3, unsigned int &cho, unsigned int &jung, unsigned int &jong)
{
  unsigned int utf16 = (byte1 & 0b00001111) << 12 | (byte2 & 0b00111111) << 6 | (byte3 & 0b00111111);

  unsigned int val = utf16 - 0xac00;

  unsigned char _jong = val % 28;
  unsigned char _jung = (val % (28 * 21)) / 28;
  unsigned char _cho =  val / (28 * 21);

  cho = 0;
  for ( int i = 0; i < 19; i++)
  {
    if ( _cho == hangul2_cho[i] )
    {
      cho = i;
    }
  }

  jung = 0;
  for ( int i = 0; i < 21; i++)
  {
    if ( _jung == hangul2_jung[i] )
    {
      jung = i;
    }
  }

  jong = 0;
  for ( int i = 0; i < 28; i++)
  {
    if ( _jong == hangul2_jong[i] )
    {
      jong = i;
    }
  }
}

void han_braille(int index1, int index2, int index3, int mod_num)
{
  bra.all_off();
  for ( int i = 0; i < 6; i++)
  {
    int on_off = hangul_cho[index1] >> (5 - i) & 0b00000001; // 초성의 점자 데이타의 i번째 비트를 가져옴
    if ( on_off != 0 )
    {
      if (flag == 0) bra.on(0, i);
      if (flag == 1) bra2.on(0, i);
      if (flag == 2) bra3.on(0, i);
    }
    else
    {
      if (flag == 0) bra.off(0, i);
      if (flag == 1) bra2.off(0, i);
      if (flag == 2) bra3.off(0, i);
    }
  }

  for ( int i = 0; i < 6; i++)
  {
    int on_off = hangul_jung[index2] >> (5 - i) & 0b00000001; // 중성의 점자 데이타의 i번째 비트를 가져옴
    if ( on_off != 0 )
    {
      if (flag == 0) bra.on(1, i);
      if (flag == 1) bra2.on(1, i);
      if (flag == 2) bra3.on(1, i);
    }
    else
    {
      if (flag == 0) bra.off(1, i);
      if (flag == 1) bra2.off(1, i);
      if (flag == 2) bra3.off(1, i);
    }
  }

  // 종성
  for ( int i = 0; i < 6; i++)
  {
    int on_off = hangul_jong[index3] >> (5 - i) & 0b00000001; // 종성의 점자 데이타의 i번째 비트를 가져옴
    if ( on_off != 0 )
    {
      if (flag == 0) bra.on(2, i);
      if (flag == 1) bra2.on(2, i);
      if (flag == 2) bra3.on(2, i);
    }
    else
    {
      if (flag == 0) bra.off(2, i);
      if (flag == 1) bra2.off(2, i);
      if (flag == 2) bra3.off(2, i);
    }
  }
  bra.refresh();
  bra2.refresh();
  bra3.refresh();
}
