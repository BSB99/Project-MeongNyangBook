document.addEventListener("DOMContentLoaded", function () {
  start();
  const myOrder = document.querySelectorAll(".col-lg-4");
  const token = Cookies.get('Authorization');

  if (token === undefined) {
    alert("로그인 해주세요");
    location.href = "/mya/view/users/sign-in";
  }

  $.ajax({
    type: "GET",
    url: `/mya/baskets`,
    headers: {
      "Authorization": token,
    }
  })
  .done((response) => {
    myOrder.forEach(container => {
      container.innerHTML = ''; // Clear existing content
    });

    if (response.basketList.length > 0) {
      let firstHtml = `
                <div class="checkout__order">
                <h4 class="order__title">영수증</h4>
                <div class="checkout__order__products">상품 <span>금액</span></div>
                <ul class="checkout__total__products">
            `;
      let itemListHtml = ``;
      let num = 1;
      let totalPrice = 0;

      for (let basket of response.basketList) {
        itemListHtml += `<li>${num}. ${basket.item.name} <span>${basket.totalPrice}원</span></li>`;
        totalPrice += basket.totalPrice;
        num++;
      }

      let lastHtml = `
                </ul>
                <ul class="checkout__total__all">
                    <li>총액 <span>${totalPrice}원</span></li>
                </ul>
                <button type="submit" class="site-btn" onclick="showKg(${totalPrice})">주문 하기</button>
                </div>
            `;

      myOrder.forEach(container => {
        container.innerHTML = firstHtml + itemListHtml + lastHtml;
      });
    }
  });
});

function createOrder() {
  const orderRecipientInput = document.getElementById("orderRecipient").value;
  const cityInput = document.getElementById("city").value;
  const streetAddressInput = document.getElementById("streetAddress").value;
  const detailAddressInput = document.getElementById("detailAddress").value;
  const phoneNumberInput = document.getElementById("phoneNumber").value;
  let additionalInfoInput = document.getElementById("additionalInfo").value;

  if (orderRecipientInput.length === 0) {
    alert("주문받는 사람은 필수사항 입니다");
    return;
  }

  if (cityInput.length === 0) {
    alert("지역은 필수사항 입니다");
    return;
  }

  if (streetAddressInput.length === 0) {
    alert("주소는 필수사항 입니다");
    return;
  }

  if (detailAddressInput.length === 0) {
    alert("상세주소는 필수사항 입니다");
    return;
  }

  if (phoneNumberInput.length === 0) {
    alert("핸드폰 번호는 필수사항 입니다");
  }

  let address = cityInput + " " + streetAddressInput + " " + detailAddressInput;
  if (additionalInfoInput.length === 0) {
    additionalInfoInput = null;
  }

  const orderRequestDto = {
    address: address,
    receiverName: orderRecipientInput,
    receiverPhoneNumber: phoneNumberInput,
    request: additionalInfoInput
  }

  return orderRequestDto;
}

function orderAjax(orderRequestDto) {
  const token = Cookies.get('Authorization');

  $.ajax({
    type: "POST",
    url: `/mya/orders`,
    headers: {
      'Content-Type': 'application/json',
      "Authorization": token,
    },
    data: JSON.stringify(orderRequestDto)
  })
  .done((response) => {
    if (response.statusCode === 201) {
      $.ajax({
        type: "DELETE",
        url: `/mya/baskets`,
        headers: {
          "Authorization": token,
        },
      })
      .done((response) => {
        if (response.statusCode === 200) {
          alert("결제 완료");
          location.href = "/mya/view/users/my-profile";
        }
      })
    }
  })
}

function showKg(totalPrice) {
  let orderRequestDto = createOrder();

  if (!orderRequestDto) {
    return;  // Exit function without proceeding to payment
  }

  IMP.init("imp08283636");

  IMP.request_pay(
      {
        pg: 'html5_inicis',
        pay_method: 'card',
        merchant_uid: 'merchant_' + new Date().getTime(),

        name: '예약 지점명 : ' + "멍냥북" + '점',
        amount: 100,
        buyer_email: "wer06099@naver.com",  /*필수 항목이라 "" 로 남겨둠*/
        //buyer_name: bookName,
      }, function (rsp) {
        //결제 성공 시
        if (rsp.success) {
          orderAjax(orderRequestDto);

          $.ajax({
            type: "GET",
            url: 'meongNyangPay',
            data: {
              amount: 100,
              imp_uid: rsp.imp_uid,
              merchant_uid: rsp.merchant_uid
            }
          });
        } else {
          var msg = '결제에 실패하였습니다.';
          msg += '에러내용 : ' + rsp.error_msg;
        }
      });
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