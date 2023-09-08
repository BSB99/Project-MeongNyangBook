const token = Cookies.get('Authorization');
document.addEventListener("DOMContentLoaded", function () {
    const itemList = document.querySelectorAll("#item-list");
    itemList.forEach(container => {
        container.innerHTML = '';
    })

    let page = 0;
    let size = 6;
    const resizeS3FirstName = "https://meongnyangs3.s3.ap-northeast-2.amazonaws.com/resize/";

    function loadItemsForPage(page) {
        $.ajax({
            type: "GET",
            url: `/mya/items?page=${page}&size=${size}`,
        })
            .done((response) => {
                let itemsHtml = '';
                for (let i = 0; i < response.itemList.length; i++) {
                    const item = response.itemList[i];
                    //리사이징된 이미지 가져오는 코드
                    let itemfileName = item.fileUrls.fileName.split(",")[0].split("/");
                    let resizeItemName = resizeS3FirstName + itemfileName[itemfileName.length - 1];

                    itemsHtml += `
        <div class="col-xl-4 col-lg-6 col-md-12 col-sm-12 col-12">
                <div class="product-thumbnail">
                  <div class="product-img-head">
                    <div class="product-img">
                      <img src="${resizeItemName}" alt="" class="img-fluid"></div>
                    <div class=""><a href="#" class="product-wishlist-btn"><i
                        class="fas fa-heart"></i></a></div>
                  </div>
                  <div class="product-content">
                    <div class="product-content-head">
                      <h3 class="product-title">${item.name}</h3>
                      <div class="product-rating d-inline-block">
                        <i class="fa fa-fw fa-star"></i>
                        <i class="fa fa-fw fa-star"></i>
                        <i class="fa fa-fw fa-star"></i>
                        <i class="fa fa-fw fa-star"></i>
                        <i class="fa fa-fw fa-star"></i>
                      </div>
                      <div class="product-price">${item.price}</div>
                    </div>
                    <div class="product-btn">
                      <button href="#" class="btn btn-primary">수정</button>
                      <button onclick="deleteItem(${item.id})" class="btn btn-outline-light">삭제</button>
                    </div>
                  </div>
                </div>
              </div>
      `;
                }

                const rowHtml = `
      <div class="row">${itemsHtml}</div>
      <div class="row">
        <div class="col-lg-12">
          <div class="product__pagination">
            ${generatePaginationLinks(response.len / 6 + 1, page)}
          </div>
        </div>
      </div>`;

                itemList.forEach(container => {
                    container.innerHTML = rowHtml;
                });
            })
            .fail((response) => {
                console.log(response);
            });
    }

    $(document).on("click", ".product__pagination a", function (event) {
        event.preventDefault();
        const newPage = parseInt($(this).data("page"));
        changePage(newPage);
    });

    function changePage(newPage) {
        page = newPage;
        loadItemsForPage(page);
    }

    // Load initial items for the first page
    loadItemsForPage(page);
})



function generatePaginationLinks(totalLen, currentPage) {
    const totalPages = totalLen; // Total number of pages

    let paginationLinks = '';
    for (let i = 1; i <= totalPages; i++) {
        if (i === currentPage + 1) {
            paginationLinks += `<a class="active" href="#">${i}</a>`;
        } else {
            paginationLinks += `<a href="#" data-page="${i - 1}">${i}</a>`;
        }
    }

    return paginationLinks;
}

function deleteItem(itemNo) {
    $.ajax({
        type: "DELETE",
        url: `/mya/items/` + itemNo,
        headers: {
            "Authorization": token,
        }
    })
        .done(res => {
            alert("아이템 삭제 성공")
            location.reload();
        })
        .fail(res => {
            alert("아이템 삭제 실패");
            console.log(res);
        })
}