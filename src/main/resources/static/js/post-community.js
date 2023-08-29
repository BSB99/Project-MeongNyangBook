document.addEventListener("DOMContentLoaded", function () {
  const host = "http://" + window.location.host;

  // let userId = 1; // board 페이지에서 받아와야 하는 값
  const token = Cookies.get('Authorization');

  $.ajax({
    type: "GET",
    url: "/mya/communities",
    headers: {'Authorization': token}
  })
  .done(function (response) {
    console.log(response);
    $('#postList').empty();
    for (let i = 0; i < response.length; i++) {
      let communityTitle = response[i]['title']; //h5
      let createdAt = response[i]['createdAt']; //span - 날짜
      let imgUrl = response[i]['fileUrls']['fileName'].split(",")[0];
      let communityId = response[i]['id']
      console.log(communityId, communityTitle, imgUrl, createdAt);
      setHtml(communityTitle, createdAt, imgUrl, communityId);
    }
  })
  .fail(function (response) {
    alert(response.responseJSON.msg);
  })
});

function setHtml(communityTitle, createdAt, imgUrl, communityId) {
  let html = `
       <div class="col-lg-4 col-md-6 col-sm-6">
        <div class="blog__item">
          <div class="blog__item__pic set-bg" >
          <img src="${imgUrl}" alt="">
          </div>
          <div class="blog__item__text" value="${communityId}">
            <span><img src="/img/icon/calendar.png" alt="">${createdAt}</span>
            <h5>${communityTitle}</h5>
            <a href="/mya/view/communities/${communityId}">Read More</a>
          </div>
        </div>
      </div>
        `;
  $('#postList').append(html);
}

// function getCommunityDetailsClick() {
//   $(".card__item").click(function () {
//         const token = Cookies.get('Authorization');
//         const communityId = this.id // 클릭된 요소의 id 값 가져오기
//         console.log(communityId);
//         // 카드 정보를 가져오기 위한 AJAX 요청
//         $.ajax({
//           url: `/mya/communities/${communityId}`,  // 카드 정보를 가져올 API 엔드포인트
//           method: 'GET',
//           headers: {"Authorization": token},
//           dataType: 'json',
//           success: function (cardDetails) {
//             window.location.href = `/okw/view/cards/${communityId}`;
//           },
//           error: function (error, status, xhr) {
//             console.error('카드 정보 가져오기 에러:', error);
//           }
//         });
//       }
//   )
// }
