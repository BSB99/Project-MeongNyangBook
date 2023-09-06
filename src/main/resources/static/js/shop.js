document.addEventListener("DOMContentLoaded", function () {
  start();
  let page = 0;
  let size = 6;
  const resizeS3FirstName = "https://meongnyangs3.s3.ap-northeast-2.amazonaws.com/resize/";
  const productContainers = document.querySelectorAll(".col-lg-9");

  function loadItemsForPage(page) {
    $.ajax({
      type: "GET",
      url: `/mya/items?page=${page}&size=${size}`,
    })
    .done((response) => {
      // Clear existing content before adding new content
      productContainers.forEach(container => {
        container.innerHTML = ''; // Clear existing content
      });
      //리사이징된 이미지 가져오는 코드
      let itemfileName = item.fileUrls.fileName.split(",")[0].split("/");
      let resizeItemName = resizeS3FirstName + itemfileName[itemfileName.length
      - 1]

      const itemsHtml = response.itemList.map(item => `
           <div class="col-lg-4 col-md-6 col-sm-6">
                    <div class="product__item sale">
                        <div class="product__item__pic set-bg">
                        <a href="/mya/view/items/${item.id}">
                        <img src="${resizeItemName}" alt="" style="cursor: pointer;" onmouseover="this.style.cursor='pointer';" onmouseout="this.style.cursor='default';">
                        </a>   
                            <ul class="product__hover"> 
                                <li><a href="#"><img src="/img/icon/heart.png" alt=""></a></li>
                                <li><a href="#"><img src="/img/icon/compare.png" alt=""> <span>Compare</span></a>
                                </li>
                                <li><a href="#"><img src="/img/icon/search.png" alt=""></a></li>
                            </ul>
                        </div>
                        <div class="product__item__text">
                            <h6>${item.name}</h6>
                            <a class="add-cart" data-itemno="${item.id}" href="#">+ Add To Cart</a>
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

  $(document).on("click", ".add-cart", function (event) {
    event.preventDefault();
    const itemNo = $(this).data("itemno");
    addCart(itemNo);
  });

  function changePage(newPage) {
    page = newPage;
    loadItemsForPage(page);
  }

  // Load initial items for the first page
  loadItemsForPage(page);
});

function addCart(itemNo) {
  const token = Cookies.get('Authorization');

  if (token === undefined) {
    alert("로그인 해주세요");
  } else {
    $.ajax({
      type: "POST",
      url: `/mya/baskets/items/${itemNo}`,
      headers: {
        "Authorization": token,
      }
    })
    .done((response) => {
      if (response.statusCode == 201) {
        alert("장바구니에 물품이 담겼습니다");
      } else {
        alert("서버에러");
      }
    })
    .fail(function (response) {
      alert(response.msg);
    })
  }
}

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