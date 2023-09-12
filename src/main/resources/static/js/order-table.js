const token = Cookies.get('Authorization');

document.addEventListener("DOMContentLoaded", function () {
    let cardtBody = document.querySelectorAll(".card-tbody");

    getOrderList(cardtBody);
})

function getOrderList(cardtBody) {
    let cardBodyHtml = "";

    $.ajax({
        type: "GET",
        url: `/mya/orders/all`,
        headers: {
            "Authorization": token,
        }
    })
        .done(res => {
            for (let i of res) {
                if (i.status === "ORDER_IN_PROGRESS") {
                    cardBodyHtml += `
                    <tr>
                    <td>${i.orderUser.orderUser}</td>
                    <td>${i.receiverName}</td>
                    <td>${i.receiverPhoneNumber}</td>
                    <td>${i.status}</td>
                    <td>${i.totalPrice}</td>
                    <td>${i.createdAt}</td>
                    <td>
                      <button class="btn btn-outline-primary rounded" onclick="patchOrderStatus(${i.id})">주문현황 변경</button>
                      <button class="btn btn-outline-primary rounded">삭제</button>
                    </td>
                  </tr>
                `;
                } else {
                    cardBodyHtml += `
                    <tr>
                    <td>${i.orderUser.orderUser}</td>
                    <td>${i.receiverName}</td>
                    <td>${i.receiverPhoneNumber}</td>
                    <td>${i.status}</td>
                    <td>${i.totalPrice}</td>
                    <td>${i.createdAt}</td>
                    <td>
                      <button class="btn btn-outline-primary rounded">삭제</button>
                    </td>
                    </tr>
                `;
                }
            }

            cardtBody.forEach(container => {
                container.innerHTML = cardBodyHtml;
            })
        })
        .fail(res => {
            alert("주문 목록 조회 중 에러 발생");
            console.log(res);
        })
}

function patchOrderStatus(orderId) {
    $.ajax({
        type: "PATCH",
        url: `/mya/orders/` + orderId,
        headers: {
            "Authorization": token,
        }
    })
        .done(res => {
            alert("상태변경 완료");
            location.reload();
        })
        .fail(res => {
            alert("상태 변경 중 에러");
            console.log(res);
        })
}