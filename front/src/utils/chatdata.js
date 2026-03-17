var setchatdatas = function (ids) {
	return [
		{
			id: 'chat_1' + ids,
			nick: 'xxx',
			from: 'me',
			cont: document.getElementById('valuemsg').value || '-'
		},
		{
			id: 'chat_2' + ids,
			nick: 'xxx',
			from: 'me',
			cont: document.getElementById('valuemsg').value || '-'
		},
		{
			id: 'chat_3' + ids,
			nick: 'xxx',
			from: 'me',
			cont: document.getElementById('valuemsg').value || '-'
		},
		{
			id: 'chat_4' + ids,
			nick: '阿珂时候的',
			from: 'else',
			cont: document.getElementById('valuemsg').value || '-'
		},
		{
			id: 'chat_5' + ids,
			nick: 'nickname',
			from: 'me',
			cont: document.getElementById('valuemsg').value || '-'
		},
		{
			id: 'chat_6' + ids,
			nick: '6chat',
			from: 'me',
			cont: document.getElementById('valuemsg').value || '-'
		},
		{
			id: 'chat_7' + ids,
			nick: 'xxx',
			from: 'me',
			cont: document.getElementById('valuemsg').value || '-'
		},
		{
			id: 'chat_8' + ids,
			nick: 'xxx',
			from: 'me',
			cont: document.getElementById('valuemsg').value || '-'
		},
		{
			id: 'chat_9' + ids,
			nick: 'xxx',
			from: 'me',
			cont: document.getElementById('valuemsg').value || '-'
		},
		{
			id: 'chat_10' + ids,
			nick: 'xxx',
			from: 'me',
			cont: document.getElementById('valuemsg').value || '-'
		}
	]
}
export { setchatdatas }