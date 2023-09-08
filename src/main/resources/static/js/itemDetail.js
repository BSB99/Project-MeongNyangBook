let itemNo;
const token = Cookies.get('Authorization');
let currnetUserNickname;
let currentUserRole;
document.addEventListener("DOMContentLoaded", function () {
  start();
  getUserInfo();
  const resizeS3FirstName = "https://meongnyangs3.s3.ap-northeast-2.amazonaws.com/resize/";
  let urlParts = window.location.href.split("/");
  itemNo = urlParts[urlParts.length - 1].replace("#", "");
  itemReviews();

  const imgList = document.querySelectorAll(".col-md-13");
  const imgDetailList = document.querySelectorAll(".col-md-9");
  const itemInfoList = document.querySelectorAll(".col-lg-8");

  imgList.forEach((container) => {
    container.innerHTML = ``;
  })
  imgDetailList.forEach((container) => {
    container.innerHTML = ``;
  })
  itemInfoList.forEach((container) => {
    container.innerHTML = ``;
  })

  let imgFirstHtml = `<ul class="nav nav-tabs" role="tablist">`;
  let imgSecondHtml = ``;
  let imgThirdHtml = ``;

  let imgDetailFirstHtml = `<div class="tab-content">`;
  let imgDetailSecondHtml = ``;
  let imgDetailThirdHtml = ``;

  let itemInfoFirstHtml = `<div class="product__details__text">`;
  let itemInfoSecondHtml = ``;

  let desc = "설명 없음";
  let no = 1;
  $.ajax({
    type: "GET",
    url: "/mya/items/" + itemNo,
    headers: {
      "Authorization": token,
    }
  })
  .done((res) => {
    console.log(res);
    for (let fileUrls of res.fileUrls.fileName.split(",")) {
      let itemfileName = fileUrls.split(",")[0].split("/");
      let resizeItemName = resizeS3FirstName + itemfileName[itemfileName.length
      - 1];
      if (no >= 2) {
        imgSecondHtml += `<li class="nav-item">
                                <a class="nav-link" data-toggle="tab" href="#tabs-${no}" role="tab">
                                    <div class="product__thumb__pic set-bg" data-setbg="${resizeItemName}" style="background-image: url('${fileUrls}')">
                                    </div>
                                </a>
                            </li>`
        imgDetailSecondHtml += `
                <div class="tab-pane" id="tabs-${no}" role="tabpanel">
                                <div class="product__details__pic__item">
                                    <img src="${fileUrls}" alt="">
                                </div>
                            </div>
                `
      } else {
        imgSecondHtml += `<li class="nav-item">
                                <a class="nav-link active" data-toggle="tab" href="#tabs-${no}" role="tab">
                                    <div class="product__thumb__pic set-bg" data-setbg="${resizeItemName}" style="background-image: url('${fileUrls}')">
                                    </div>
                                </a>
                            </li>`
        imgDetailSecondHtml += `
                <div class="tab-pane active" id="tabs-${no}" role="tabpanel">
                                <div class="product__details__pic__item">
                                    <img src="${fileUrls}" alt="">
                                </div>
                            </div>
                `
      }
      no++;
    }
    itemInfoSecondHtml += `
            <h4>${res.name}</h4>
                            <div class="rating">
                                <i class="fa fa-star"></i>
                                <i class="fa fa-star"></i>
                                <i class="fa fa-star"></i>
                                <i class="fa fa-star"></i>
                                <i class="fa fa-star-o"></i>
                            </div>
                            <h3>${res.price}원</h3>
                            <p>남은 수량(${res.quantity})</p>
                            <p>${res.content === undefined ? desc : res.content}</p>
                            <div class="product__details__cart__option">
                                <button onclick="addCart(${res.id})" class="primary-btn">카드 담기</button>
                            </div>
                        </div>
            `;
    imgThirdHtml += `</ul>`;
    imgDetailThirdHtml += `</div>`
    imgList.forEach(container => {
      container.innerHTML = imgFirstHtml + imgSecondHtml + imgThirdHtml;
    });

    imgDetailList.forEach(container => {
      container.innerHTML = imgDetailFirstHtml + imgDetailSecondHtml
          + imgDetailThirdHtml;
    });

    itemInfoList.forEach(container => {
      container.innerHTML = itemInfoFirstHtml + itemInfoSecondHtml;
    });
  })
})

function start() {
  const auth = Cookies.get('Authorization');
  console.log("auth=", auth);

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

function itemReviews() {
  let reviews = document.querySelectorAll(".product_review");
  let reviewHtml = '';

  $.ajax({
    type: "GET",
    url: "/mya/reviews/all/" + itemNo,
  })
  .done(res => {
    if (res.length === 0) {
      reviewHtml += `
           <h3 style="padding-top: 200px; text-align: center; font-size: 40px;">아이템을 구매하시고 리뷰의 첫 작성자가 되어보세요!</h3>
          `;

      reviews.forEach(container => {
        container.innerHTML = reviewHtml
      })
    } else {
      for (let i of res) {
        reviewHtml += `
          <img src=""/>
          <div className="bubble" class="bubble">
            닉네임 : ${i.nickname}<br>
            제목 : ${i.title}<br>
            내용 : ${i.description}<br>
            별점 : ${"⭐".repeat(i.starRating)}
          </div>`;
      }

      reviews.forEach(container => {
        container.innerHTML = "<div class=\"message\">" + reviewHtml + "</div>"
      })
    }
  })
  .fail(res => {
    alert("리뷰 조회 중 에러")
    console.log(res);
  })
}

function addCart(itemNo) {
  const token = Cookies.get('Authorization');

  if (token === undefined) {
    alert("로그인 해주세요");
  } else {
    $.ajax({
      type: "POST",
      url: `/mya/baskets/items/${itemNo}`,
      headers: {
        "Authorization": token,
      }
    })
    .done((response) => {
      if (response.statusCode == 201) {
        alert("장바구니에 물품이 담겼습니다");
      } else {
        alert("서버에러");
      }
    })
    .fail(function (response) {
      alert(response.msg);
    })
  }
}

function getInquiry() {
  const inquiryBody = document.querySelectorAll(".product__details__tab__content__inquiry");
  let inquiryBodyHtml = "";
  $.ajax({
    type: "GET",
    url: `/mya/inquiries/all/` + itemNo,
    headers: {
      "Authorization": token,
    }
  })
      .done(res => {
        for (let inquiry of res) {
          inquiryBodyHtml += `
          <div>
          <p>title : ${inquiry.title}</p>
          <button onclick="getInquiryInfo(${inquiry.inquiryId})">상세보기</button>
          </div>
          `
        }

        inquiryBody.forEach(container => {
          container.innerHTML = inquiryBodyHtml;
        })
      })
      .fail(res => {
        alert("문의 조회 실패");
        console.log(res);
      })
}

function saveInquiry() {
  let userInputTitle = document.getElementById("inputTitle").value;
  let userInputContent = document.getElementById("inputQuestion").value;
  const requestDto = {
    "title":userInputTitle,
    "description":userInputContent
  }
  $.ajax({
    type: "POST",
    url: `/mya/inquiries/${itemNo}`,
    headers: {
      "Authorization": token,
    },
    contentType: "application/json",
    data: JSON.stringify(requestDto)
  })
      .done(res => {
        alert("문의 작성 완료");
        location.reload();
      })
      .fail(res => {
        alert("문의 등록 중 에러 발생");
        console.log(res);
      })
}

function showInquiry() {
  $('#writeInquiryModal').modal('show');
}

function closeInquiryModal() {
  $('#writeInquiryModal').modal('hide');
}
let inquiryNo;
function getInquiryInfo(inquiryId) {
  $('#viewInquiryModal').modal('show');
  let getInquiryBody = document.querySelectorAll(".modal-body");
  let getInquiryFooter = document.querySelectorAll(".modal-footer");
  let getInquiryBodyHtml = "";
  let getInquirtCommentBodtHtml = "";
  inquiryNo = inquiryId;
  $.ajax({
    type: "GET",
    url: `/mya/inquiries/` + inquiryId,
    headers: {
      "Authorization": token,
    }
  })
      .done(res => {
        console.log(res);
        if(currentUserRole !== "ADMIN") {
          getInquiryFooter.forEach(container => {
            container.innerHTML = "";
          })
        }

        if(currnetUserNickname === res.nickname) {
          getInquiryBodyHtml = `
          <div class="mb-3">
          <label for="inputTitle" class="form-label">작성자</label>
          <p>${res.nickname}</p>
          </div>
          <div class="mb-3">
          <label for="inputTitle" class="form-label">제목</label>
          <p>${res.title}</p>
          </div>
          <div class="mb-3">
          <label for="inputQuestion" class="form-label">내용</label>
          <p>${res.description}</p>
          </div>
          <button onclick="deleteInquiry(${inquiryId})">삭제하기</button>
          `;
        } else {
          getInquiryBodyHtml = `
          <div class="mb-3">
          <label for="inputTitle" class="form-label">작성자</label>
          <p>${res.nickname}</p>
          </div>
          <div class="mb-3">
          <label for="inputTitle" class="form-label">제목</label>
          <p>${res.title}</p>
          </div>
          <div class="mb-3">
          <label for="inputQuestion" class="form-label">내용</label>
          <p>${res.description}</p>
          </div>`
        }

        getInquirtCommentBodtHtml += `
        <br>
        <div class="mb-3" style="padding-top: 25px;">
          <label for="inputQuestion" class="form-label">댓글</label>
          <p>관리자 / ${res.comment.contents}</p>
          </div>
        `
        getInquiryBody.forEach(container => {
          container.innerHTML = getInquiryBodyHtml + getInquirtCommentBodtHtml;
        })
      })
      .fail(res => {
        alert("문의 조회 실패");
        console.log(res);
      })
}

function closeGetInquiryModal() {
  $('#viewInquiryModal').modal('hide');
}

function getUserInfo() {
  $.ajax({
    type: "GET",
    url: "/mya/users",
    headers: {"Authorization": token}
  })
      .done((res) => {
        currnetUserNickname = res.nickname;
        currentUserRole = res.role;
      })
      .fail(function (response, status, xhr) {
        alert("유저정보 가져오기 실패");
        console.log(response);
      })
}

function saveComment() {
  const commentValue = document.getElementById("saveComment").value;

  const requestDto = {
    "contents" : commentValue
  }
  $.ajax({
    type: "POST",
    url: "/mya/inquiry-comment/" + inquiryNo,
    headers: {"Authorization": token},
    data: JSON.stringify(requestDto),
    contentType: "application/json"
  })
      .done(res => {
        location.reload();
      })
      .fail(res => {
        alert("답변 작성 실패");
        console.log(res);
      })
}

function deleteInquiry(inquiryId) {
  $.ajax({
    type: "DELETE",
    url: `/mya/inquiries/` + inquiryId,
    headers: {
      "Authorization": token,
    }
  })
      .done(res => {
        location.reload();
      })
      .fail(res => {
        alert("문의를 삭제하는 도중 에러 발생");
        console.log(res);
      })
}