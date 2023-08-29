document.addEventListener("DOMContentLoaded", function () {
    const myOrder = document.querySelectorAll(".col-lg-4");
    const token = Cookies.get('Authorization');

    if (token === undefined) {
        alert("로그인 해주세요");
        location.href="/mya/view/users/sign-in";
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
                <button type="submit" class="site-btn" onclick="createOrder()">주문 하기</button>
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

    if(orderRecipientInput.length === 0) {
        alert("주문받는 사람은 필수사항 입니다");
        return;
    }

    if(cityInput.length === 0) {
        alert("지역은 필수사항 입니다");
        return;
    }

    if(streetAddressInput.length === 0) {
        alert("주소는 필수사항 입니다");
        return;
    }

    if(detailAddressInput.length === 0) {
        alert("상세주소는 필수사항 입니다");
        return;
    }

    if(phoneNumberInput.length === 0) {
        alert("핸드폰 번호는 필수사항 입니다");
    }

    let address = cityInput+ " " + streetAddressInput + " " + detailAddressInput;
    if (additionalInfoInput.length === 0) {
        additionalInfoInput = null;
    }

    const token = Cookies.get('Authorization');

    const orderRequestDto = {
        address : address,
        receiverName : orderRecipientInput,
        receiverPhoneNumber : phoneNumberInput,
        request : additionalInfoInput
    }

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
            if(response.statusCode === 201) {
                $.ajax({
                    type: "DELETE",
                    url: `/mya/baskets`,
                    headers: {
                        "Authorization": token,
                    },
                })
                    .done((response) => {
                        if(response.statusCode === 200) {
                            alert("주문 생성 완료");
                            location.href = "/";
                        }
                    })
            }
        })
}