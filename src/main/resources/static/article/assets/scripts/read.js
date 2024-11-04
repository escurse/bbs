const $cover = document.getElementById('cover');
const $passwordDialog = document.getElementById('passwordDialog');
const $main = document.getElementById('main');

{
    const $modifyButton = $main.querySelector('button[name="modify"]');
    const $deleteButton = $main.querySelector('button[name="delete"]');
    $passwordDialog.onsubmit = (e) => {
        e.preventDefault();
        const xhr = new XMLHttpRequest();
        xhr.onreadystatechange = () => {
            if (xhr.readyState !== XMLHttpRequest.DONE) {
                return;
            }
            if (xhr.status < 200 || xhr.status >= 300) {
                alert("오류 발생")
                return;
            }
        }
        xhr.open('GET', location.href);
        xhr.send();
    }
}