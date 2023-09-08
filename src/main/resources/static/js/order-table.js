const token = Cookies.get('Authorization');

document.addEventListener("DOMContentLoaded", function () {
    let cardtBody = document.querySelectorAll(".card-tbody");
    /*cardtBody.forEach(container => {
        container.innerHTML = "";
    })*/
    getOrderList(cardtBody);
})

function getOrderList(cardtBody) {
    $.ajax({
        type: "GET",
        url: `/mya/orders/all`,
        headers: {
            "Authorization": token,
        }
    })
        .done(res => {
            for (let i of res) {
                console.log(i);
            }
        })
        .fail(res => {
            alert("주문 목록 조회 중 에러 발생");
            console.log(res);
        })
}