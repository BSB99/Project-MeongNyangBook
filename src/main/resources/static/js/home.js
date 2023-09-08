const token = Cookies.get('Authorization');

function logout() {

  $.ajax({
    type: "POST",
    url: "/mya/auth/logout",
    headers: {'Authorization': token}
  })
  .done(function (response, status, xhr) {
    Cookies.remove('Authorization', {path: '/'});
    // console.log(response.get("msg"));

    window.location.href = "/";
  })
  .fail(function (response) {
    alert('로그아웃 오류: ' + response);
    console.log(response);
  })
}

function parseJwt(token) {
  var base64Url = token.split('.')[1];
  var base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
  var jsonPayload = decodeURIComponent(
      window.atob(base64).split('').map(function (c) {
        return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
      }).join(''));

  return JSON.parse(jsonPayload);
}

document.addEventListener("DOMContentLoaded", function () {
  start();
  let page = 0;
  let size = 3;
  $.ajax({
    type: "GET",
    url: `/mya/communities?page=${page}&size=${size}`,
    headers: {'Authorization': token}
  })
  .done(function (response) {
    $('#community_last').empty();
    for (let i = 0; i < 3; i++) {
      console.log(response.responseList[i]);
      let communityTitle = response.responseList[i].title; //h5
      let createdAt = response.responseList[i].createdAt; //span - 날짜
      let communityId = response.responseList[i].id;
      let imgUrl = response.responseList[i].fileUrls.fileName.split(
          ",")[0].split("/");

      let resizeImg;
      if (imgUrl === null) {
        resizeImg = "https://s3-us-west-2.amazonaws.com/s.cdpn.io/245657/1_copy.jpg";
      } else {
        resizeImg = "https://meongnyangs3.s3.ap-northeast-2.amazonaws.com/resize/"
            + imgUrl[imgUrl.length - 1]
      }

      let html = `
      <div class="col-lg-4 col-md-6 col-sm-6">
        <div class="blog__item">
          <div class="blog__item__pic set-bg"
               data-setbg="/img/blog/blog-1.jpg"></div>
          <div class="blog__item__text">
            <span><img src="${resizeImg}"
                       alt="">"${createdAt}"</span>
            <h5>"${communityTitle}"</h5>
            <a href="/mya/view/communities/${communityId}">Read More</a>
          </div>
        </div>
      </div>
    `;
      $('#community_last').append(html);
    }
  })
  .fail(function (response) {
    alert(response.responseJSON.msg);
  })
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
