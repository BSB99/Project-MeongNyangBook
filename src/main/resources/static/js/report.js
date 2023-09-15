const token = Cookies.get("Authorization");

document.addEventListener("DOMContentLoaded", function () {
  getReportInfo();
});

function getReportInfo() {

  $.ajax({
    type: "GET",
    url: "/mya/reports",
    headers: {'Authorization': token}
  })
  .done(function (response) {
    $('#report_info_table').empty();

    for (let res of response) {

      let temp_html =
          `<tr>
                    <td>${res.id}</td>
                    <td>${res.user}</td>
                    <td>${res.description}</td>
                    <td>${res.reportedUser}</td>
                    <td>${res.reportCategory}</td>
                    <td>${res.createdAt}</td>
                    <td>
                      <button onclick="blockUser('${res.reportedUser}')" class="btn btn-outline-primary rounded">영구정지
                      </button>
                      <button onclick="deleteReport(${res.id})" class="btn btn-outline-primary rounded">취소
                      </button>
                    </td>
                  </tr>`

      $('#report_info_table').append(temp_html);

    }
  })
  .fail(function (response) {
    alert(response.msg);
  })
}

function blockUser(reportedUsername) {
  $.ajax({
    type: "PUT",
    url: "/mya/auth/block?blockUserName=" + reportedUsername,
    headers: {'Authorization': token},
  })
  .done(res => {
    let emailRequestDto = {
      email: reportedUsername,
      status: true
    }
    $.ajax({
      type: "POST",
      url: "/mya/auth/email",
      contentType: "application/json",
      data: JSON.stringify(emailRequestDto)
    })
    .done(res => {
      alert("해당유저 엉구정지 성공");
    })
    .fail(res => {
      alert('유저 영구정지 Email오류!');
      console.log("상태 코드 " + res.status + ": " + res.message);
    })
  })
  .fail(res => {
    console.log(reportedUsername);
    alert('유저 영구정지 오류!');
    console.log("상태 코드 " + res.status + ": " + res.message);
  })
}

function deleteReport(reportId) {
  $.ajax({
    url: "/mya/reports/" + reportId, // 백엔드 endpoint로 수정
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