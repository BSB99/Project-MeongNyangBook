const token = Cookies.get('Authorization');
document.addEventListener("DOMContentLoaded", function () {
  start();
  const host = "http://" + window.location.host;
  const resizeS3FirstName = "https://meongnyangs3.s3.ap-northeast-2.amazonaws.com/resize/";

  let size = 9;

  // 페이지 데이터를 로드하는 로직을 별도의 함수로 분리
  function loadPageData(page) {
    $.ajax({
      type: "GET",
      url: `/mya/communities?page=${page}&size=${size}`,
      headers: {'Authorization': token}
    })
    .done(function (response) {
      $('#postList').empty();

      for (let i = 0; i < response.content.length; i++) {
        const community = response.content[i];
        let communityTitle = community.title
        let createdAt = community.createdAt;
        let imgUrl = community.fileUrls.fileName.split(",")[0].split("/");
        let communityId = community.id;
        setHtml(communityTitle, createdAt,
            resizeS3FirstName + imgUrl[imgUrl.length - 1], communityId);
      }

      // 페이지네이션 링크 생성 및 추가
      let len = response.totalElements;
      let paginationHtml = generatePaginationLinks(len / size + 1, page);
      $('#postList').append(paginationHtml);
    })
    .fail(function (response) {
      alert(response.responseJSON.msg);
    });
  }

  // 초기 페이지 데이터 로드
  loadPageData(0);

  // 페이지네이션 링크 클릭 이벤트 리스너 추가
  $(document).on('click', 'button[data-page]', function (e) {
    e.preventDefault();
    let selectedPage = parseInt($(this).data('page'));
    loadPageData(selectedPage);
  });
});

function setHtml(communityTitle, createdAt, imgUrl, communityId) {
  let html = `
       <div class="col-lg-4 col-md-6 col-sm-6">
        <div class="blog__item">
          <div class="blog__item__pic set-bg" >
          <img src="${imgUrl}" alt="" style="height: 240.19px">
          </div>
          <div class="blog__item__text" value="${communityId}">
            <span><img src="/img/icon/calendar.png" alt="">${createdAt}</span>
            <h5>${communityTitle}</h5>
            <a href="/mya/view/communities/${communityId}">Read More</a>
          </div>
        </div>
      </div>`;
  $('#postList').append(html);
}

function start() {
  const auth = Cookies.get('Authorization');

  if (!auth) { // 쿠키가 없을 경우
    document.getElementById('login-text').style.display = 'block';
    document.getElementById('logout-text').style.display = 'none';
    document.getElementById('mypage-text').style.display = 'none';
  } else { // 쿠키가 있을 경우
    document.getElementById('login-text').style.display = 'none';
    document.getElementById('logout-text').style.display = 'block';
    document.getElementById('mypage-text').style.display = 'block';

    const postBoxes = document.getElementsByClassName('postbox');
    for (let i = 0; i < postBoxes.length; i++) {
      postBoxes[i].style.display = 'block';
    }
  }
}

function generatePaginationLinks(totalLen, currentPage) {
  const totalPages = Math.ceil(totalLen); // Total number of pages

  let paginationLinks = '<div class="pagination-container" style="width: 2000px">';
  for (let i = 1; i <= totalPages; i++) {
    if (i === currentPage + 1) {
      paginationLinks += `<button class="pagination-btn active" data-page="${i
      - 1}">${i}</button>`;
    } else {
      paginationLinks += `<button class="pagination-btn" data-page="${i
      - 1}">${i}</button>`;
    }
  }
  paginationLinks += '</div>';

  return paginationLinks;
}