let itemNo;

document.addEventListener("DOMContentLoaded", function() {
    const token = Cookies.get('Authorization');

    let urlParts = window.location.href.split("/");
    itemNo = urlParts[urlParts.length - 1].replace("#", "");

    const imgList = document.querySelectorAll(".col-md-3");
    const imgDetailList = document.querySelectorAll(".col-md-9");
    imgList.forEach((container) => {
        container.innerHTML = ``;
    })
    imgDetailList.forEach((container) => {
        container.innerHTML = ``;
    })

    let imgFirstHtml = `<ul class="nav nav-tabs" role="tablist">`;
    let imgSecondHtml = ``;
    let imgThirdHtml = ``;

    let imgDetailFirstHtml = `<div class="tab-content">`;
    let imgDetailSecondHtml = ``;
    let imgDetailThirdHtml = ``;
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
                imgSecondHtml += `<li class="nav-item">
                                <a class="nav-link active" data-toggle="tab" href="#tabs-${no}" role="tab">
                                    <div class="product__thumb__pic set-bg" data-setbg="${fileUrls}">
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
                no++;
            }

            imgThirdHtml += `</ul>`;
            imgDetailThirdHtml += `</div>`
            imgList.forEach(container => {
                container.innerHTML = imgFirstHtml + imgSecondHtml + imgThirdHtml;
            });

            imgDetailList.forEach(container => {
                container.innerHTML = imgDetailFirstHtml + imgDetailSecondHtml + imgDetailThirdHtml;
            });
        })
})