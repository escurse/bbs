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
}
