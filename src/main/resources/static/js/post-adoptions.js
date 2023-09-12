document.addEventListener("DOMContentLoaded", function () {
  start();
  const host = "http://" + window.location.host;
  const resizeS3FirstName = "https://meongnyangs3.s3.ap-northeast-2.amazonaws.com/resize/";
  // let userId = 1; // board 페이지에서 받아와야 하는 값
  const token = Cookies.get('Authorization');

  let page = 0;
  let size = 9;
  let postContainers = document.querySelectorAll("#adoptionPostList");

  function goToPage(newPage) {
    page = newPage; // 페이지 번호 업데이트
    loadPostForPage(page); // 새 페이지 로드
  }

  function loadPostForPage(page) {

    $.ajax({
      type: "GET",
      url: `/mya/adoptions?page=${page}&size=${size}`,
    })
    .done((response) => {
      // Clear existing content before adding new content

      let temp_html = '';

      for (let i = 0; i < response.responseList.length; i++) {
        const adoption = response.responseList[i];
        //리사이징된 이미지 가져오는 코드
        let adoptionfileName = adoption.fileUrls.fileName.split(",")[0].split(
            "/");
        let resizeItemName = resizeS3FirstName
            + adoptionfileName[adoptionfileName.length - 1];

        temp_html += `
        <div class="col-lg-4 col-md-6 col-sm-6">
        <div class="blog__item">
          <div class="blog__item__pic set-bg" >
          <img src="${resizeItemName}" style="height: 240.19px;" alt="">
          </div>
          <div class="blog__item__text" value="${adoption.id}">
            <span><img src="/img/icon/calendar.png" alt="">${adoption.createdAt}</span>
            <h5>${adoption.title}</h5>
            <a href="/mya/view/adoptions/${adoption.id}">Read More</a>
          </div>
        </div>
      </div>
      `;
      }
      const rowHtml = `
      <div class="row">${temp_html}</div>
      <div class="row">
        <div class="col-lg-12">
          <div class="product__pagination">
            ${generatePaginationLinks(response.len / size + 1,
          page)}
          </div>
        </div>
      </div>`;

      postContainers.forEach(container => {
        container.innerHTML = rowHtml;
      });
    })
    .fail((response) => {
      console.log(response);
    });
  }

  loadPostForPage(page);

  document.addEventListener("click", function (event) {
    if (event.target && event.target.matches(".product__pagination a")) {
      event.preventDefault();
      const newPage = parseInt(event.target.getAttribute("data-page"));
      if (!isNaN(newPage)) {
        goToPage(newPage);
      }
    }
  });
});


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
  const totalPages = totalLen; // Total number of pages

  let paginationLinks = '<div class="pagination-container" style="width: 2000px; margin: 10px -20%;">';
  for (let i = 1; i <= totalPages; i++) {
    if (i === currentPage + 1) {
      paginationLinks += `<a class="active" href="#">${i}</a>`;
    } else {
      paginationLinks += `<a href="#" data-page="${i
      - 1}">${i}</a>`;
    }
  }

  return paginationLinks;
}