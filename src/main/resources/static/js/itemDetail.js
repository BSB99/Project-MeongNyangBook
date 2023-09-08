let itemNo;

document.addEventListener("DOMContentLoaded", function () {
  start();
  const token = Cookies.get('Authorization');
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