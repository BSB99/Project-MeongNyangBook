document.addEventListener("DOMContentLoaded", function () {
  let page = 0;
  let size = 6;
  const productContainers = document.querySelectorAll(".col-lg-9");

  function loadItemsForPage(page) {
    $.ajax({
      type: "GET",
      url: `/mya/items?page=${page}&size=${size}`,
    })
    .done((response) => {
      console.log(response);
      // Clear existing content before adding new conte성nt
      productContainers.forEach(container => {
        container.innerHTML = ''; // Clear existing content
      });

      const itemsHtml = response.itemList.map(item => `
           <div class="col-lg-4 col-md-6 col-sm-6">
                    <div class="product__item sale">
                        <div class="product__item__pic set-bg">
                        <img src="${item.fileUrls.fileName.split(",")[0]}" alt="">    
                            <ul class="product__hover"> 
                                <li><a href="#"><img src="/img/icon/heart.png" alt=""></a></li>
                                <li><a href="#"><img src="/img/icon/compare.png" alt=""> <span>Compare</span></a>
                                </li>
                                <li><a href="#"><img src="/img/icon/search.png" alt=""></a></li>
                            </ul>
                        </div>
                        <div class="product__item__text">
                            <h6>${item.name}</h6>
                            <a href="#" class="add-cart">+ Add To Cart</a>
                            <div class="rating">
                                <i class="fa fa-star"></i>
                                <i class="fa fa-star"></i>
                                <i class="fa fa-star"></i>
                                <i class="fa fa-star"></i>
                                <i class="fa fa-star-o"></i>
                            </div>
                            <h5>${item.price}원</h5>
                            <div class="product__color__select">
                                <label for="pc-7">
                                    <input type="radio" id="pc-7">
                                </label>
                                <label class="active black" for="pc-8">
                                    <input type="radio" id="pc-8">
                                </label>
                                <label class="grey" for="pc-9">
                                    <input type="radio" id="pc-9">
                                </label>
                            </div>
                        </div>
                    </div>
                </div>
        `).join('');
      const rowHtml = `
                <div class="row">${itemsHtml}</div>
                <div class="row">
                  <div class="col-lg-12">
                    <div class="product__pagination">
                      ${generatePaginationLinks(response.len / 6 + 1, page)}
                    </div>
                  </div>
                </div>`;

      productContainers.forEach(container => {
        container.innerHTML = rowHtml;
      });
    })
    .fail((response) => {
      console.log(response);
    });
  }

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
});
