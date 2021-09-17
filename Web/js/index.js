$(document).ready(() => {
    // 드롭 다운 영역 클릭 시 이벤트
    let drop_zone = $("#dropZone");
    drop_zone.on('click', () => {
        $('#imageFileOpenInput').trigger('click');
    })

    init();
})

// 파일 드롭 다운
function fileDropDown() {
    let drop_zone = $("#dropZone");

    // enter
    drop_zone.on('dragenter', function(e) {
        e.stopPropagation();
        e.preventDefault();
        // 드롭다운 영역 css
        drop_zone.css('background-color', '#E3F2FC');
    });

    // leave
    drop_zone.on('dragleave', function(e) {
        e.stopPropagation();
        e.preventDefault();
        // 드롭다운 영역 css
        drop_zone.css('background-color', '#FFFFFF');
    });

    // over
    drop_zone.on('dragover', function(e) {
        e.stopPropagation();
        e.preventDefault();
        // 드롭다운 영역 css
        drop_zone.css('background-color', '#E3F2FC');
    });

    // drop
    drop_zone.on('drop', (e) => {
        e.preventDefault();
        // 드롭다운 영역 css
        drop_zone.css('background-color', '#FFFFFF');

        let files = e.originalEvent.dataTransfer.files;
        $('#imageFileOpenInput').prop("files", files);
    });
}

function init(){
    fileDropDown();
}