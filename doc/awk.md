
```
cat bus.log |
	grep '/api/driver/grab/v2/grab-order/aggregation-list|GrabOrderListReqV2' |
	grep 'GB60c1ef973af8e0171ab696ea' |
	grep '2021-06-10 19:' | 
	awk -F '|' '{print $12}'
```

```

cat bus.log |
	grep '/api/driver/grab/v2/grab-order/aggregation-list|GrabOrderListReqV2' |
	grep 'grabActivityStatusDesc=抢单中' | 
	awk -F '|' '{print $9}' | sort | uniq -c | sort -nr|head -50
```
