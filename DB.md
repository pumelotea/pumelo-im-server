### 用户表
```
{
	uid,
	name,
	sex,
	birth,
	phone,
	password,
	salt
}
```
### 好友表(需要双向)
```
{
	firendId,
	uid,
	firendUid,
}
```
### 群组表
```
{
	groupId,
	groupName,
	uid
	
}
```
### 群组-成员表
```
{
	memberId,
	groupId,
	uid,
}
```