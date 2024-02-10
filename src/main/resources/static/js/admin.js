// 왼쪽 sidebar에 맞는 메뉴 눌렀을 때, 오른쪽 dashboard의 각 영역에 맞게 자연스럽게 스크롤
document.querySelector('.total_stats_wrap').addEventListener('click', function() {
    let location = document.querySelector('.platform_total_stats_container').offsetTop;

    window.scrollTo({top: location, behavior: 'smooth'});
});

document.querySelector('.member_wrap').addEventListener('click', function() {
    let location = document.querySelector('.member_management_title').offsetTop;

    window.scrollTo({top: location, behavior: 'smooth'});
});

document.querySelector('.institution_wrap').addEventListener('click', function() {
   let location = document.querySelector('.institution_management_title').offsetTop;

   window.scrollTo({top: location, behavior: 'smooth'});
});

document.querySelector('.lecture_wrap').addEventListener('click', function() {
    let location = document.querySelector('.lecture_management_title').offsetTop;

    window.scrollTo({top: location, behavior: 'smooth'});
});

// table th의 체크박스 눌렀을 때, 전체 체크되게
const member_all_checkbox = document.querySelector('#member_all_checkbox');

member_all_checkbox.addEventListener('click', function() {
    const isChecked = member_all_checkbox.checked;
    const checkboxes = document.querySelectorAll('.member_checkbox');

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

const institution_all_checkbox = document.querySelector('#institution_all_checkbox');

institution_all_checkbox.addEventListener('click', function() {
    const isChecked = institution_all_checkbox.checked;
    const checkboxes = document.querySelectorAll('.institution_checkbox');

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

const lecture_all_checkbox = document.querySelector('#lecture_all_checkbox');

lecture_all_checkbox.addEventListener('click', function() {
    const isChecked = lecture_all_checkbox.checked;
    const checkboxes = document.querySelectorAll('.lecture_checkbox');

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