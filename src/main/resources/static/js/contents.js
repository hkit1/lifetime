// 왼쪽 sidebar에 맞는 메뉴 눌렀을 때, 오른쪽 dashboard의 각 영역에 맞게 자연스럽게 스크롤
document.querySelector('.total_stats_wrap').addEventListener('click', function() {
    let location = document.querySelector('.institution_container').offsetTop;

    window.scrollTo({top: location, behavior: 'smooth'});
});

document.querySelector('.member_attendance_wrap').addEventListener('click', function() {
    let location = document.querySelector('.member_attendance_container').offsetTop;

    window.scrollTo({top: location, behavior: 'smooth'});
});

document.querySelector('.test_wrap').addEventListener('click', function() {
    let location = document.querySelector('.test_container').offsetTop;

    window.scrollTo({top: location, behavior: 'smooth'});
});

document.querySelector('.evaluation_wrap').addEventListener('click', function() {
    let location = document.querySelector('.evaluation_container').offsetTop;

    window.scrollTo({top: location, behavior: 'smooth'});
});

// table th의 체크박스 눌렀을 때, 전체 체크되게
const attendance_all_checkbox = document.querySelector('#attendance_all_checkbox');

attendance_all_checkbox.addEventListener('click', function() {
    const isChecked = attendance_all_checkbox.checked;
    const checkboxes = document.querySelectorAll('.attendance_checkbox');

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

const test_all_checkbox = document.querySelector('#test_all_checkbox');

test_all_checkbox.addEventListener('click', function() {
    const isChecked = test_all_checkbox.checked;
    const checkboxes = document.querySelectorAll('.test_checkbox');

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

const evaluation_all_checkbox = document.querySelector('#evaluation_all_checkbox');

evaluation_all_checkbox.addEventListener('click', function() {
    const isChecked = evaluation_all_checkbox.checked;
    const checkboxes = document.querySelectorAll('.evaluation_checkbox');

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