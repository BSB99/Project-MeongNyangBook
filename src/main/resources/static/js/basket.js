const token = Cookies.get('Authorization');
document.addEventListener("DOMContentLoaded", function () {
  start();
  const shopCart = document.querySelectorAll(".shopping-cart");

  if (token === undefined) {
    alert("로그인 후 이용해주세요");
    location.href="/mya/view/users/sign-in";
  } else {
    $.ajax({
      type: "GET",
      url: `/mya/baskets`,
      headers: {
        "Authorization": token,
      }
    })
    .done((response) => {
      shopCart.forEach(container => {
        container.innerHTML = ''; // Clear existing content
      });

      let itemHtml = '';
      let totalPrice = 0;

      for (let basket of response.basketList) {
        if (basket.item) {
          itemHtml += `
                        <tr>
                            <td class="product__cart__item">
                                <div class="product__cart__item__pic">
                                    <a href="/mya/view/items/${basket.item.id}"><img src="${basket.item.fileUrls.fileName.split(
              ",")[0]}" style="height: 100px; weight: 100px" alt=""></a>
                                </div>
                                <div class="product__cart__item__text">
                                    <h6>${basket.item.name}</h6>
                                    <h5>${basket.item.price}원</h5>
                                </div>
                            </td>
                            <td class="quantity__item">
                                <div class="quantity">
                                    <div class="pro-qty-2">
                                        <input type="text" value="${basket.cnt}">
                                    </div>
                                </div>
                            </td>
                            <td class="cart__price">${basket.totalPrice}원</td>
                            <td class="cart__close">
                            <button class="fa fa-close" onclick="deleteItem(${basket.item.id})"> </button>
                            </td>
                        </tr>
                        `;
          totalPrice += basket.totalPrice;
        }
      }

      const totalCashHtml = `
                <div class="col-lg-4">
                    <div class="cart__total" style="margin-top: 50px">
                        <h6>총 가격</h6>
                        <ul>
                            <li>Total <span>${totalPrice}원</span></li>
                        </ul>
                    </div>
                </div>
                `;

      let buttonHtml = `
                <div class="row">
                    <div class="col-lg-6 col-md-6 col-sm-6">
                        <div class="continue__btn">
                            <a href="/mya/view/items" class="btn btn-primary">쇼핑으로 돌아가기</a>
                        </div>
                    </div>
                `;

      // Only show the order button if totalPrice is greater than 0
      if (totalPrice > 0) {
        buttonHtml += `
                    <div class="col-lg-6 col-md-6 col-sm-6">
                        <div class="continue__btn update__btn">
                            <a href="/mya/view/order" class="btn btn-success"><i class="fa fa-spinner"></i>주문하기</a>
                        </div>
                    </div>
                    `;
      }

      buttonHtml += `</div>`; // Close the .row div

      const cartHtml = `
                <div class="row">
                    <div class="col-lg-8">
                        <div class="shopping__cart__table">
                            <table class="table">
                                <thead>
                                    <tr>
                                        <th style="padding-left: 180px">물품</th>
                                        <th>수량</th>
                                        <th>가격</th>
                                        <th></th>
                                    </tr>
                                </thead>
                                <tbody>
                                    ${itemHtml}
                                </tbody>
                            </table>
                        </div>
                        ${buttonHtml}
                    </div>
                    ${totalCashHtml}
                </div>
            </div>
            </section>
                `;

      shopCart.forEach(container => {
        container.innerHTML = cartHtml;
      });
    });
  }
});

function deleteItem(itemId) {
  $.ajax({
    type: "DELETE",
    url: `/mya/baskets/items/${itemId}`,
    headers: {
      "Authorization": token,
    }
  })
  .done((response) => {
    if (response.statusCode === 200) {
      alert(response.msg);
      location.reload();
    }
  })
  .fail(function (response) {
    alert(response.msg);
  })
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