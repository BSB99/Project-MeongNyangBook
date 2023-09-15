const resizeS3FirstName = "https://meongnyangs3.s3.ap-northeast-2.amazonaws.com/resize/";
document.addEventListener("DOMContentLoaded", function () {

  const host = "http://" + window.location.host;
  myCommunity();
  // let userId = 1; // board 페이지에서 받아와야 하는 값
  const token = Cookies.get('Authorization');
  if (token === undefined) {
    alert("로그인 후 이용해주세요");
    location.href = "/mya/view/users/sign-in";
  }
  $.ajax({
    type: "GET",
    url: "/mya/auth/profile",
    headers: {'Authorization': token}
  })
  .done(function (response) {
    document.getElementById("nickname").value = response.nickname;
    document.getElementById("introduce").value = response.introduce;

    document.getElementById("address").value = response.address;
    document.getElementById("phone-number").value = response.phoneNumber;

    document.getElementById("modal_nickname").value = response.nickname;
    document.getElementById("modal_introduce").value = response.introduce;
    document.getElementById("modal_address").value = response.address;

    let fileNames = response.fileList.split("/");
    resizeFileName = resizeS3FirstName + fileNames[fileNames.length - 1];

    document.getElementById("user-image").src = resizeFileName;

  })
  .fail(function (response) {
    alert(response.responseJSON.msg);
  })

  // 수정 버튼 클릭시 필드 값 변경 & 버튼 전환

  const doneButtons = document.querySelectorAll('.done-btn');
  doneButtons.forEach(function (button) {
    button.addEventListener('click', doneButtons);
  });
})

function editProfile() {
  const token = Cookies.get("Authorization");
  const nickname = document.getElementById("modal_nickname");
  const address = document.getElementById("modal_address");
  const introduce = document.getElementById("modal_introduce");

  let formData = new FormData();
  const profileRequestDto = {
    nickname: nickname.value,
    address: address.value,
    introduce: introduce.value
  };

  if ($('input[type="file"]')[0].files.length === 0) {
    const emptyBlob = new Blob([""], {type: "application/octet-stream"});
    formData.append("fileName", emptyBlob, "meongnyangbook.png");
  } else {
    formData.append('fileName', $('input[type="file"]')[0].files[0]);
  }

  formData.append("profileRequestDto", JSON.stringify(profileRequestDto));

  $.ajax({
    type: "PUT",
    url: "/mya/auth/profile",
    contentType: false,
    data: formData,
    headers: {'Authorization': token},
    processData: false
  })
  .done(function (xhr) {
    window.location.reload();

  })
  .fail(function (xhr) {
    alert('프로필 수정 오류!');
    alert("상태 코드 " + xhr.status + ": " + xhr.responseJSON.message);
  });
}

function myCommunity() {
  const token = Cookies.get("Authorization");
  $.ajax({
    type: "GET",
    url: "/mya/communities/my-post",
    headers: {'Authorization': token}
  })
  .done(function (response) {
    $('.gallery').empty();

    for (let res of response) {
      let postId = res.id;

      let splitName = res.fileUrls.fileName.split(",")[0];
      let resizefile = splitName.replace(
          "https://meongnyangs3.s3.ap-northeast-2.amazonaws.com/",
          resizeS3FirstName);
      let temp_html =
          `<div onclick="moveCommunityPost(${postId})" class="gallery-item" tabIndex="0">
        <img
            src="${resizefile}"
            class="gallery-image"
            alt=""/>
        <div class="gallery-item-info">
          <ul>
            <li class="gallery-item-likes">
                    <span class="visually-hidden">Like:</span>
                    <i class="fas fa-heart" aria-hidden="true"></i> ${res.likeCount}
            </li>
            <li class="gallery-item-comments">
                    <span class="visually-hidden">Comments:</span>
                    <i class="fas fa-comment" aria-hidden="true"></i> ${res.commentCount}
          </ul>
        </div>
      </div>`

      $('.gallery').append(temp_html);

    }
  })
  .fail(function (response) {
    console.log(response.responseJSON.msg)
  })
}

function myAdoption() {
  const token = Cookies.get("Authorization");
  $.ajax({
    type: "GET",
    url: "/mya/adoptions/my-post",
    headers: {'Authorization': token}
  })
  .done(function (response) {
    $('.gallery').empty();

    for (let res of response) {
      let postId = res.id;
      let splitName = res.fileUrls.fileName.split(",")[0];
      let resizefile = splitName.replace(
          "https://meongnyangs3.s3.ap-northeast-2.amazonaws.com/",
          resizeS3FirstName);

      let temp_html =
          `<div onclick="moveAdoptionPost(${postId})" class="gallery-item" tabIndex="0">
        <img
            src="${resizefile}"
            class="gallery-image"
            alt=""/>
        <div class="gallery-item-info">
          <ul>
            <li class="gallery-item-likes">
                    <span class="visually-hidden">Like:</span
                    ><i class="fas fa-heart" aria-hidden="true"></i> ${res.likeCount}
            </li>
            <li class="gallery-item-comments">
                    <span class="visually-hidden">Comments:</span
                    ><i class="fas fa-comment" aria-hidden="true"></i> ${res.commentCount}
            </li>
          </ul>
        </div>
      </div>`

      $('.gallery').append(temp_html);

    }
  })
  .fail(function (response) {
    console.log(response)
  })
}

function openEditModal() {
  $('.modale').addClass('opened');
}

function clsEditModal() {
  $('.modale').removeClass('opened');
}

function myOrders() {
  const token = Cookies.get("Authorization");
  $.ajax({
    type: "GET",
    url: "/mya/orders",
    headers: {'Authorization': token},
  })
  .done(res => {
    $('.gallery').empty();
    for (let order of res.orderList) {
      let itemfileName = order.orderItemList[0].item.fileUrls.fileName.split(
          ",")[0].split("/");
      let resizeItemName = resizeS3FirstName + itemfileName[itemfileName.length
      - 1];

      let galleryFirstHtml = `
        <div class="gallery-item" tabIndex="0" onclick="openItemModal(${order.id})" >
          <img src="${resizeItemName}" class="gallery-image" alt=""/>
          <div class="gallery-item-info">
            <ul>
              <li class="gallery-item-likes">
                <span class="visually-hidden">수령인:</span>
                <i class="fas fa-heart" aria-hidden="true"></i> ${order.receiverName}
              </li>
              <li class="gallery-item-comments">
                <span class="visually-hidden">가격:</span>
                <i class="fas fa-money-bill-alt" aria-hidden="true"></i> ${order.totalPrice}
              </li>
            </ul>
          </div>
        </div>
      `;

      $('.gallery').append(galleryFirstHtml);
    }
  })
  .fail(res => {
    console.log("상태 코드 " + res.status + ": " + res.responseJSON.message);
  });
}

function openItemModal(orderId) {
  const token = Cookies.get("Authorization");
  // 모달 열기
  $('#orderModal').addClass('opened');
  let itemModalBody = document.querySelectorAll("#itme-modal-detail")

  itemModalBody.forEach(container => {
    container.innerHTML = "";
  })

  let itemModalData = "";
  let itemModalDetailData = "";
  $.ajax({
    type: "GET",
    url: "/mya/orders/" + orderId,
    headers: {'Authorization': token},
  })
  .done(res => {
    itemModalData += `
        <p>수령인 : ${res.receiverName}</p>
        <p>수령인 전화번호 : ${res.receiverPhoneNumber}</p>
        <p>총 금액 : ${res.totalPrice}원</p>
        <p>주문상황 : ${res.status === "ORDER_IN_PROGRESS" ? "주문 완료" : "배송 완료"}
        `

    for (let i of res.orderItemList) {
      let itemName = i.item.name;
      let itemCnt = i.itemCnt;
      let itemPrice = i.itemPrice;
      if (res.status === "ORDER_IN_PROGRESS") {
        itemModalDetailData += `
            <li class="list-item">
                <span>상품 : <a href="/mya/view/items/${i.item.id}">${itemName}</a></span>
                <span>가격 : ${itemPrice}원</span>
                <span>주문 개수 : ${itemCnt}개</span>
            </li>
            `;
      } else {
        itemModalDetailData += `
            <li class="list-item">
                <span>상품 : <a href="/mya/view/items/${i.item.id}">${itemName}</a></span>
                <span>가격 : ${itemPrice}원</span>
                <span>주문 개수 : ${itemCnt}개</span>
                <span><button name="리뷰쓰기" class="btn" onclick="openReviewModal(${i.item.id})">리뷰쓰기</button></span>
            </li>
            `;
      }
    }

    itemModalBody.forEach(container => {
      container.innerHTML = itemModalData + "<ul>" + itemModalDetailData
          + "</ul>";
    })
  })
  .fail(res => {
    console.log("상태 코드 " + res.status + ": " + res.responseJSON.message);
  })
}

function closeItemModal() {
  // 모달 열기
  $('#orderModal').removeClass("opened");

}

function closeReviewModal() {
  // 모달 닫기
  $('#reviewModal').removeClass("opened");
}

let starCnt = 0;
let reviewItemId = 0;

function openReviewModal(itemId) {
  reviewItemId = itemId;
  // 모달 열기
  $('#reviewModal').addClass('opened');
  const stars = document.querySelectorAll('.star');
  const ratingInput = document.getElementById('rating');

  stars.forEach(star => {
    star.addEventListener('click', () => {
      const rating = parseInt(star.getAttribute('data-rating'));
      ratingInput.value = rating;
      starCnt = rating;
      updateStarRating(stars, rating);
    });
  });
}

function updateStarRating(stars, rating) {
  stars.forEach(star => {
    const starRating = parseInt(star.getAttribute('data-rating'));
    if (starRating <= rating) {
      star.textContent = '★';
    } else {
      star.textContent = '☆';
    }
  });
}

function submitReview() {
  const token = Cookies.get("Authorization");
  const reviewTitle = document.getElementById('reviewTitle').value;
  const reviewText = document.getElementById('review').value;

  if (starCnt === 0 || !reviewTitle || !reviewText) {
    alert("리뷰 별점과 내용, 제목을 모두 입력해야 합니다.");
    return; // 리뷰 전송하지 않고 함수 종료
  }
  const requestDto = {
    "title": reviewTitle,
    "description": reviewText,
    "starRating": starCnt
  }
  $.ajax({
    type: "POST",
    url: "/mya/reviews/" + reviewItemId,
    headers: {'Authorization': token},
    data: JSON.stringify(requestDto),
    contentType: "application/json"
  })
  .done(res => {
    alert("리뷰 작성 성공");
    closeReviewModal();
  })
  .fail(res => {
    alert('리뷰 전달 오류!');
    console.log("상태 코드 " + res.status + ": " + res.responseJSON.message);
  })
}

function moveCommunityPost(postId) {
  window.location.href = "/mya/view/communities/" + postId;
}

function moveAdoptionPost(postId) {
  window.location.href = "/mya/view/adoptions/" + postId;
}