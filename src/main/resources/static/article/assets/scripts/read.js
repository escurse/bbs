const $cover = document.getElementById('cover');
const $passwordDialog = document.getElementById('passwordDialog');
const $main = document.getElementById('main');

{
    const $modifyButton = $main.querySelector('button[name="modify"]');
    const $deleteButton = $main.querySelector('button[name="delete"]');
    $deleteButton.onclick = () => {
        $cover.classList.add('--visible');
        $passwordDialog['mode'].value = 'delete';
        $passwordDialog['password'].value = '';
        $passwordDialog.classList.add('--visible');
    };
}

{
    $passwordDialog['cancel'].onclick = () => {
        $cover.classList.remove('--visible');
        $passwordDialog.classList.remove('--visible');
    }

    $passwordDialog.onsubmit = (e) => {
        e.preventDefault();
        if ($passwordDialog['password'].value === '') {
            alert('비밀번호를 입력해 주세요.');
            $passwordDialog['password'].focus();
            return;
        }
        if ($passwordDialog['mode'].value === 'delete') {
            const xhr = new XMLHttpRequest();
            const formData = new FormData();
            formData.append('index', $passwordDialog['index'].value);
            formData.append('password', $passwordDialog['password'].value);
            xhr.onreadystatechange = () => {
                if (xhr.readyState !== XMLHttpRequest.DONE) {
                    return;
                }
                if (xhr.status < 200 || xhr.status >= 300) {
                    alert('게시글을 삭제하지 못했습니다. 잠시 후 다시 시도해 주세요.');
                    return;
                }
                const response = JSON.parse(xhr.responseText);
                switch (response['result']) {
                    case 'failure':
                        alert('게시글을 삭제하지 못했습니다. 이미 삭제된 게시글일 수도 있습니다. 잠시 후 다시 시도해 주세요.')
                        break;
                    case 'failure_password':
                        alert('게시글을 삭제하지 못했습니다. 암호가 올바르지 않습니다.');
                        break;
                    case 'success':
                        alert('게시글을 성공적으로 삭제했습니다.');
                        location.href = $main.querySelector('.button.back').href; // 목록 앵커 태그의 링크
                        break;
                    default:
                        alert('서버가 알 수 없는 응답을 반환했습니다. 삭제 결과를 반드시 확인해 주세요.');
                        break;
                }
            }
            xhr.open('DELETE', './read'); // /article/read
            xhr.send(formData);
        }
        if ($passwordDialog['mode'].value === 'modify') {
            const xhr = new XMLHttpRequest();
            xhr.onreadystatechange = () => {
                if (xhr.readyState !== XMLHttpRequest.DONE) {
                    return;
                }
                if (xhr.status < 200 || xhr.status >= 300) {
                    alert('오류 발생');
                    return;
                }
            }
            xhr.open('GET', location.href);
            xhr.send();
        }
    }
}
