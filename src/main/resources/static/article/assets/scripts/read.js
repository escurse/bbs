const $cover = document.getElementById('cover');
const $passwordDialog = document.getElementById('passwordDialog');
const $main = document.getElementById('main');

{
    const $modifyButton = $main.querySelector('button[name="modify"]');
    const $deleteButton = $main.querySelector('button[name="delete"]');
    $modifyButton.onclick = () => {
        $cover.classList.add('--visible');
        $passwordDialog['mode'].value = 'modify';
        $passwordDialog['password'].value = '';
        $passwordDialog.classList.add('--visible');
    };
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
            const url = new URL(location.href);
            url.pathname = '/article/modify';
            url.searchParams.set('index', $passwordDialog['index'].value);
            url.searchParams.set('password', $passwordDialog['password'].value);
            location.href = url.toString();
        }
        if ($passwordDialog['mode'].value === 'deleteComment') {
            const xhr = new XMLHttpRequest();
            const formData = new FormData();
            formData.append('index', $passwordDialog['commentIndex'].value);
            formData.append('password', $passwordDialog['password'].value);
            xhr.onreadystatechange = () => {
                if (xhr.readyState !== XMLHttpRequest.DONE) {
                    return;
                }
                if (xhr.status < 200 || xhr.status >= 300) {
                    alert('댓글을 삭제하지 못했습니다. 잠시 후 다시 시도해 주세요.');
                    return;
                }
                const response = JSON.parse(xhr.responseText);
                switch (response['result']) {
                    case 'failure':
                        alert('댓글을 삭제하지 못했습니다. 이미 삭제된 게시글일 수도 있습니다. 잠시 후 다시 시도해 주세요.');
                        break;
                    case 'failure_password':
                        alert('댓글을 삭제하지 못했습니다. 암호가 올바르지 않습니다.');
                        break;
                    case 'success':
                        alert('댓글을 성공적으로 삭제했습니다.');
                        loadComments();
                        break;
                    default:
                        alert('서버가 알 수 없는 응답을 반환했습니다. 삭제 결과를 반드시 확인해 주세요.');
                        break;
                }
            }
            xhr.open('DELETE', '../comment/');
            xhr.send(formData);
            $cover.classList.remove('--visible');
            $passwordDialog.classList.remove('--visible');
        }
    }
}


const $list = $main.querySelector(':scope > .comment-container > .list');
const appendComment = (comment) => {
    const i = comment['index'];
    const $item = new DOMParser().parseFromString(`            <li class="item">
                <div class="top">
                    <span class="nickname">${comment['nickname']}</span>
                    <span class="spring"></span>
                    <span class="datetime">${comment['createdAt']}</span>
                </div>
                <div class="content">${comment['content']}</div>
                <div class="action-container">
                    <input checked id="commentNoAction${i}" name="comment${i}" type="radio" value="noAction">
                    <label class="action">
                        <input name="comment${i}" type="radio" value="reply">
                        <span class="text">답글 쓰기</span>
                    </label>
                    <label class="action">
                        <input name="comment${i}" type="radio" value="modify">
                        <span class="text">수정</span>
                    </label>
                    <button class="action" name="delete" type="button">삭제</button>
                </div>
                <form class="form reply">
                    <input name="commentIndex" type="hidden" value="${comment['index']}">
                    <div class="writer-wrapper">
                        <label class="label">
                            <span class="text">작성자</span>
                            <input required type="text" class="field" name="nickname" minlength="1" maxlength="10">
                        </label>
                        <label class="label">
                            <span class="text">비밀번호</span>
                            <input required type="password" class="field" name="password" minlength="4" maxlength="50">
                        </label>
                    </div>
                    <label class="label spring">
                        <span class="text">내용</span>
                        <textarea required class="field" maxlength="100" minlength="1" name="content"></textarea>
                    </label>
                    <div class="button-container">
                        <button class="--obj-button -blue" type="submit">답글 쓰기</button>
                        <label class="--obj-button -light" for="commentNoAction${i}">취소</label>
                    </div>
                </form>
                <form class="form modify">
                    <div class="writer-wrapper">
                        <label class="label">
                            <span class="text">작성자</span>
                            <input required type="text" class="field" name="nickname" minlength="1" maxlength="10" value="${comment['nickname']}">
                        </label>
                        <label class="label">
                            <span class="text">비밀번호</span>
                            <input required type="password" class="field" name="password" minlength="4" maxlength="50">
                        </label>
                    </div>
                    <label class="label spring">
                        <span class="text">내용</span>
                        <textarea required class="field" maxlength="100" minlength="1" name="content">${comment['content']}</textarea>
                    </label>
                    <div class="button-container">
                        <button class="--obj-button -blue" type="submit" name="modify">댓글 수정</button>
                        <label class="--obj-button -light" for="commentNoAction${i}" name="delete">취소</label>
                    </div>
                </form>
            </li>
`, 'text/html').querySelector('li.item');
    const $replyForm = $item.querySelector('.form.reply');
    $replyForm.onsubmit = (e) => {
        e.preventDefault();
        postComment($replyForm);
    }
    const $modifyForm = $item.querySelector('.form.modify');
    $modifyForm.onsubmit = (e) => {
        e.preventDefault();
    }
    const $deleteButton = $item.querySelector('[name="delete"]');
    $deleteButton.onclick = () => {
        $cover.classList.add('--visible');
        $passwordDialog['mode'].value = 'deleteComment';
        $passwordDialog['commentIndex'].value = comment['index'];
        $passwordDialog['password'].value = '';
        $passwordDialog.classList.add('--visible');
    }
    $list.append($item);
    return $item;
};

const appendComments = (allComments, targetComments, step) => {
    for (const comment of targetComments) {
        appendComment(comment).style.marginLeft = `${step * 50}px`;
        const subComments = allComments.filter((x) => x['commentIndex'] === comment['index']);
        appendComments(allComments, subComments, step + 1);
    }
}

const loadComments = () => {
    const url = new URL(location.href);
    $list.innerHTML = '';
    const xhr = new XMLHttpRequest();
    xhr.onreadystatechange = () => {
        if (xhr.readyState !== XMLHttpRequest.DONE) {
            return;
        }
        if (xhr.status < 200 || xhr.status >= 300) {
            alert('댓글 정보를 불러오지 못했습니다. 잠시 후 다시 시도해 주세요.');
            return;
        }
        const allComments = JSON.parse(xhr.responseText);
        const rootComments = allComments.filter((x) => x['commentIndex'] == null); // 대댓글 제외
        appendComments(allComments, rootComments, 0);
    };
    xhr.open('GET', `../comment/comments?articleIndex=${url.searchParams.get('index')}`);
    xhr.send();
};

loadComments();

const postComment = ($form) => {
    if ($form['nickname'].value === '') {
        alert('작성자를 입력해 주세요.');
        return;
    }
    if ($form['password'].value === '') {
        alert('비밀번호를 입력해 주세요.');
        return;
    }
    if ($form['content'].value === '') {
        alert('내용을 입력해 주세요.');
        return;
    }
    const url = new URL(location.href);
    const xhr = new XMLHttpRequest();
    const formData = new FormData();
    if ($form['commentIndex'] != null) {
        //     != null === (!== null && !== undefined)
        formData.append('commentIndex', $form['commentIndex'].value);
    }
    formData.append('articleIndex', url.searchParams.get('index'));
    formData.append('nickname', $form['nickname'].value);
    formData.append('password', $form['password'].value);
    formData.append('content', $form['content'].value);
    xhr.onreadystatechange = () => {
        if (xhr.readyState !== XMLHttpRequest.DONE) {
            return;
        }
        if (xhr.status < 200 || xhr.status >= 300) {
            alert('댓글을 작성하지 못했습니다. 잠시 후 다시 시도해 주세요.');
            return;
        }
        $form['content'].value = '';
        $form['content'].focus();
        loadComments();
    };
    xhr.open('POST', '../comment/');
    xhr.send(formData);
};

const $commentForm = document.getElementById('commentForm');
$commentForm.onsubmit = (e) => {
    e.preventDefault();
    postComment($commentForm);
}

