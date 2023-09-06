document.addEventListener("DOMContentLoaded", function () {
  start();
  const host = "http://" + window.location.host;
  const resizeS3FirstName = "https://meongnyangs3.s3.ap-northeast-2.amazonaws.com/resize/";
  // let userId = 1; // board 페이지에서 받아와야 하는 값
  const token = Cookies.get('Authorization');

  $.ajax({
    type: "GET",
    url: "/mya/adoptions",
    headers: {'Authorization': token}
  })
  .done(function (response) {
    console.log(response);
    $('#adoptionPostList').empty();
    for (let i = 0; i < response.length; i++) {
      let adoptionTitle = response[i]['title']; //h5
      let createdAt = response[i]['createdAt']; //span - 날짜
      let imgUrl = response[i]['fileUrls']['fileName'].split(",")[0].split("/");

      let adoptionId = response[i]['id']
      console.log(adoptionId, adoptionTitle,
          resizeS3FirstName + imgUrl[imgUrl.length - 1], createdAt);
      setHtml(adoptionTitle, createdAt,
          resizeS3FirstName + imgUrl[imgUrl.length - 1], adoptionId);
    }
  })
  .fail(function (response) {
    alert(response.responseJSON.msg);
  })
});

function setHtml(adoptionTitle, createdAt, imgUrl, adoptionId) {
  let html = `
       <div class="col-lg-4 col-md-6 col-sm-6">
        <div class="blog__item">
          <div class="blog__item__pic set-bg" >
          <img src="${imgUrl}" alt="">
          </div>
          <div class="blog__item__text" value="${adoptionId}">
            <span><img src="/img/icon/calendar.png" alt="">${createdAt}</span>
            <h5>${adoptionTitle}</h5>
            <a href="/mya/view/adoptions/${adoptionId}">Read More</a>
          </div>
        </div>
      </div>
        `;
  $('#adoptionPostList').append(html);
}

function start() {
  const auth = Cookies.get('Authorization');
  console.log("auth=", auth);

  if (!auth) { // 쿠키가 없을 경우
    console.log(1);
    document.getElementById('login-text').style.display = 'block';
    document.getElementById('logout-text').style.display = 'none';
    document.getElementById('mypage-text').style.display = 'none';
  } else { // 쿠키가 있을 경우
    console.log(2);
    document.getElementById('login-text').style.display = 'none';
    document.getElementById('logout-text').style.display = 'block';
    document.getElementById('mypage-text').style.display = 'block';

    const postBoxes = document.getElementsByClassName('postbox');
    for (let i = 0; i < postBoxes.length; i++) {
      postBoxes[i].style.display = 'block';
    }
  }
}