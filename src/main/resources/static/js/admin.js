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