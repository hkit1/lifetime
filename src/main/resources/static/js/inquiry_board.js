// 게시판 제목, 작성자에 마우스 올리면 밑줄 생기게 하기
const text = document.querySelectorAll('tbody tr td a');

for (let i = 0; i < text.length; i++) {
    text[i].addEventListener('mouseover', function () {
        text[i].style.textDecoration = 'underline';
    });

    text[i].addEventListener('mouseout', function () {
        text[i].style.textDecoration = 'none';
    });
}

// table th의 체크박스 눌렀을 때, 전체 체크되게
const inquiry_all_checkbox = document.querySelector('#inquiry_all_checkbox');

inquiry_all_checkbox.addEventListener('click', function() {
    const isChecked = inquiry_all_checkbox.checked;
    const checkboxes = document.querySelectorAll('.inquiry_checkbox');

    if (isChecked) {
        for (const checkbox of checkboxes) {
            checkbox.checked = true;
        }
    }
    else {
        for (const checkbox of checkboxes) {
            checkbox.checked = false;
        }
    }
});