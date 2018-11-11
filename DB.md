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
	remarkName
}
```
### 群组表
```
{
	groupId,
	groupName,
}
```

### 用户-群组表
```
{
	userGroupId,
	uid,
	groupId
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