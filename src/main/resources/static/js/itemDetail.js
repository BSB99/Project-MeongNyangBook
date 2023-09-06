let itemNo;

document.addEventListener("DOMContentLoaded", function () {
  start();
  const token = Cookies.get('Authorization');

  let urlParts = window.location.href.split("/");
  itemNo = urlParts[urlParts.length - 1].replace("#", "");

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
    for (let fileUrls of res.fileUrls.fileName.split(",")) {
      if (no >= 2) {
        imgSecondHtml += `<li class="nav-item">
                                <a class="nav-link" data-toggle="tab" href="#tabs-${no}" role="tab">
                                    <div class="product__thumb__pic set-bg" data-setbg="${fileUrls}" style="background-image: url('${fileUrls}')">
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
                                    <div class="product__thumb__pic set-bg" data-setbg="${fileUrls}" style="background-image: url('${fileUrls}')">
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
                                <span> - 5 Reviews</span>
                            </div>
                            <h3>${res.price}원</h3>
                            <p>남은 수량(${res.quantity})</p>
                            <p>${res.content === undefined ? desc : res.content}</p>
                            <div class="product__details__cart__option">
                                <div class="quantity">
                                    <div class="pro-qty">
                                        <input type="text" value="1">
                                    </div>
                                </div>
                                <a href="#" class="primary-btn">add to cart</a>
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