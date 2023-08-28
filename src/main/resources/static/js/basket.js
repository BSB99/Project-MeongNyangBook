document.addEventListener("DOMContentLoaded", function () {
    const shopCart = document.querySelectorAll(".shopping-cart");
    const token = Cookies.get('Authorization');

    if (token === undefined) {
        alert("로그인 해주세요");
        window.location.href = "/mya/view/users/sign-in"
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
                    totalPrice += basket.totalPrice;
                    itemHtml += `
                    <tr>
                        <td class="product__cart__item">
                            <div class="product__cart__item__pic">
                                <img src="/img/shopping-cart/cart-1.jpg" alt="">
                            </div>
                            <div class="product__cart__item__text">
                                <h6>${basket.item.name}</h6>
                                <h5>${basket.item.price}원</h5>
                            </div>
                        </td>
                        <td class="quantity__item">
                            <div class="quantity">
                                <div class="pro-qty-2">
                                    <input type="text" value="1">
                                </div>
                            </div>
                        </td>
                        <td class="cart__price">${basket.totalPrice}원</td>
                        <td class="cart__close"><i class="fa fa-close"></i></td>
                    </tr>
                `;
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

                const buttonHtml = `
                <div class="row">
                    <div class="col-lg-6 col-md-6 col-sm-6">
                        <div class="continue__btn">
                            <a href="/mya/view/items" class="btn btn-primary">쇼핑으로 돌아가기</a>
                        </div>
                    </div>
                    <div class="col-lg-6 col-md-6 col-sm-6">
                        <div class="continue__btn update__btn">
                            <a href="/mya/view/order" class="btn btn-success"><i class="fa fa-spinner"></i>주문하기</a>
                        </div>
                    </div>
                </div>
            `;

                const cartHtml = `
                <div class="row">
                    <div class="col-lg-8">
                        <div class="shopping__cart__table">
                            <table class="table">
                                <thead>
                                    <tr>
                                        <th style="padding-left: 40px">물품</th>
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
