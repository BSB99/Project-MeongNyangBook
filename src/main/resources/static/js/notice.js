const token = Cookies.get("Authorization");
let noticeId;
document.addEventListener("DOMContentLoaded", function () {
  getNotice();
  //create-modal

  // modify-modal
  const exampleModal = document.getElementById('exampleModal');
  if (exampleModal) {
    exampleModal.addEventListener('show.bs.modal', event => {
      // Button that triggered the modal
      const button = event.relatedTarget
      // Extract info from data-bs-* attributes
      const recipient = button.getAttribute('data-bs-whatever')
      // If necessary, you could initiate an Ajax request here
      // and then do the updating in a callback.

      // Update the modal's content.
      const modalTitle = exampleModal.querySelector('.modal-title')
      const modalBodyInput = exampleModal.querySelector('.modal-body input')

      modalTitle.textContent = `공지 수정`
      modalBodyInput.value = ""
    })
  }
});

function getNotice() {
  $.ajax({
    type: "GET",
    url: "/mya/backoffice/notices",
    headers: {'Authorization': token}
  })
  .done(function (response) {
    $('#notice_info_table').empty();
    for (let res of response) {

      let temp_html =
          `<tr>
                    <td>${res.id}</td>
                    <td>${res.title}</td>
                    <td>${res.description}</td>
                    <td>${res.createdAt}</td>
                    <td>
                    <button onclick="saveId(${res.id})" type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#exampleModal" data-bs-whatever="@mdo">수정</button>
<!--                      <button onclick="modifyModalOpen()" class="btn btn-outline-primary rounded">수정</button>-->
                      <button onclick="deleteNotice(${res.id})" class="btn btn-outline-primary rounded">삭제</button>
                    </td>`

      $('#notice_info_table').append(temp_html);

    }
  })
  .fail(function (response) {
    alert(response.msg);
  })
}

function saveId(id) {
  noticeId = id;
}

//공지 수정
function modifyNotice() {

  let title = document.getElementById("recipient-name").value;
  let description = document.getElementById("message-text").value;

  let requestDto = {
    "title": title,
    "description": description
  }

  $.ajax({
    type: "PUT",
    url: "/mya/backoffice/notices/" + noticeId,
    headers: {'Authorization': token},
    data: JSON.stringify(requestDto),
    contentType: "application/json"
  })
  .done(res => {
    alert("공지수정 성공");
    window.location.reload();
  })
  .fail(res => {
    alert('공지수정 오류!');
    console.log("상태 코드 " + res.status + ": " + res.message);
  })
}

//공지 삭제
function deleteNotice(id) {
  $.ajax({
    url: "/mya/backoffice/notices/" + id, // 백엔드 endpoint로 수정
    type: "DELETE",
    headers: {"Authorization": token},
    success: function (response) {
      alert('삭제가 완료 되었습니다!', response);
      // 다른 성공 동작 처리
      window.location.reload();
    },
    error: function (error) {
      alert('Delete Failed');
      console.error("Error:", error);
      // 다른 에러 동작 처리
    }
  });
}

function createNotice() {
  const noticeTitle = document.getElementById('notice_title').value;
  const noticeDescription = document.getElementById('notice_description').value;

  if (!noticeTitle || !noticeDescription) {
    alert("제목과 내용을 모두 입력해주세요.");
    return;
  }

  const requestDto = {
    "title": noticeTitle,
    "description": noticeDescription,
  }

  $.ajax({
    type: "POST",
    url: "/mya/backoffice/notices",
    headers: {'Authorization': token},
    data: JSON.stringify(requestDto),
    contentType: "application/json"
  })
  .done(res => {
    alert("공지 등록 성공");
    window.location.reload();
  })
  .fail(res => {
    alert('공지 등록 오류!');
    console.log("상태 코드 " + res.status + ": " + res.responseJSON.message);
  })
}