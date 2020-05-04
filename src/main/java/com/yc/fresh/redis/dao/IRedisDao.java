package com.yc.fresh.redis.dao;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;

public interface IRedisDao {
	/**
	 * 存储字符串对象
	 * @param key
	 * @param value
	 */
	public void set(String key, Object value);

	/**
	 * 存字符串对象
	 * @param key 键
	 * @param value 值
	 * @param timeout 失效时间
	 */
	public void set(String key, Object value, int timeout);

	/**
	 * 同时存储多个键值对
	 * Map<String, Object> maps = new HashMap<String, Object>();
	 * maps.put("multi1","multi1");
	 * maps.put("multi2","multi2");
	 * maps.put("multi3","multi3");
	 * @param map
	 */
	public void multiSet(Map<String, Object> maps);

	/**
	 * 根据键取值
	 * @param key
	 * @return
	 */
	public Object get(String key);

	/**
	 * 根据键列表取出值列表
	 * List<String> keys = new ArrayList<String>();
	 * Collections.addAll(keys, "multi1", "multi2", "multi3");
	 * @param keys
	 */
	public void multiGet(List<String> keys);

	/**
	 * 设置键的字符串值并返回其旧值
	 * @param key 键
	 * @param value 新值
	 * @return
	 */
	public Object getAndSet(String key, Object value);

	/**
	 * 让键自增 支持整数
	 * @param key
	 * @param delta
	 * @return
	 */
	public Long increment(String key, long delta);

	/**
	 * 让键自增 支持浮点型
	 * @param key
	 * @param delta
	 * @return
	 */
	public Double increment(String key, double delta);

	/**
	 * 如果key已经存在并且是一个字符串，则该命令将该值追加到字符串的末尾。
	 * 如果键不存在，则它被创建并设置为空字符串，因此APPEND在这种特殊情况下将类似于SET。
	 * @param key
	 * @param delta
	 * @return
	 */
	public Integer append(String key, String value);

	/**
	 * 截取key所对应的value字符串
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	public String get(String key, long start, long end);

	/**
	 * 返回key所对应的value值得长度
	 * @param key
	 * @return
	 */
	public Long size(String key);

	/**
	 * 返回存储在键中的列表的指定元素。偏移开始和停止是基于零的索引，其中0是列表的第一个元素（列表的头部），1是下一个元素
	 * 如：range("list",0,-1)
	 * @param <T>
	 * @param key
	 * @param start
	 * @param end -1 说明到最后
	 * @return
	 */
	public <T> List<T> listRange(String key, long start, long end);

	/**
	 * 修剪现有列表，使其只包含指定的指定范围的元素，起始和停止都是基于0的索引
	 * trim("list",1,-1); // 裁剪第一个元素
	 * @param key
	 * @param start
	 * @param end
	 */
	public void listTrim(String key, long start, long end);

	/**
	 * 返回存储在键中的列表的长度。如果键不存在，则将其解释为空列表，并返回0。当key存储的值不是列表时返回错误。
	 * @param key
	 * @return
	 */
	public Long listSize(String key);

	/**
	 * 将所有指定的值插入存储在键的列表的头部。如果键不存在，则在执行推送操作之前将其创建为空列表。（从左边插入）
	 * leftPush("list","java")
	 * @param key
	 * @param value
	 * @return
	 */
	public Long listLeftPush(String key, Object value);

	/**
	 * 批量把一个数组插入到列表中
	 * String[] stringarrays = new String[]{"1","2","3"};
	 * eftPushAll("listarray",stringarrays)
	 * leftPush("list","java")
	 * @param key
	 * @param value
	 * @return
	 */
	public Long listLeftPushAll(String key, Object ... values);

	/**
	 * 批量把一个list插入到列表中
	 * leftPush("list","java")
	 * @param key
	 * @param value
	 * @return
	 */
	public Long listLeftPushAll(String key, Collection<Object> values);

	/**
	 * 把value值放到key对应列表中pivot值的左面，如果pivot值存在的话
	 * @param key
	 * @param pivot
	 * @param value
	 * @return
	 */
	public Long listLeftPush(String key, Object pivot, Object value);

	/**
	 * 将所有指定的值插入存储在键的列表的头部。如果键不存在，则在执行推送操作之前将其创建为空列表。（从右边插入）
	 * @param key
	 * @param value
	 * @return
	 */
	public Long listRightPush(String key, Object value);

	/**
	 * 批量把一个数组插入到列表中
	 * @param key
	 * @param value
	 * @return
	 */
	public Long listRightPushAll(String key, Object ... values);

	/**
	 * 批量把一个list插入到列表中
	 * leftPush("list","java")
	 * @param key
	 * @param value
	 * @return
	 */
	public Long listRightPushAll(String key, Collection<Object> values);

	/**
	 * 把value值放到key对应列表中pivot值的右面，如果pivot值存在的话
	 * @param key
	 * @param pivot
	 * @param value
	 * @return
	 */
	public Long listRightPush(String key, Object pivot, Object value);

	/**
	 * 在列表中index的位置设置value值
	 * @param key
	 * @param index
	 * @param value
	 */
	public void listSet(String key, long index, Object value);

	/**
	 *  从存储在键中的列表中删除等于值的元素的第一个计数事件。
	 *  计数参数以下列方式影响操作：
	 *  count > 0：删除等于从头到尾移动的值的元素。
	 *  count < 0：删除等于从尾到头移动的值的元素。
	 *  count = 0：删除等于value的所有元素。
	 *  remove("listRight",1,"setValue");// 将删除列表中存储的列表中第一次出现的“setValue”。
	 * @param key
	 * @param count
	 * @param value
	 * @return
	 */
	public Long listRemove(String key, long count, Object value);

	/**
	 * 根据下表获取列表中的值，下标是从0开始的
	 * @param key
	 * @param index
	 * @return
	 */
	public Object listIndex(String key, long index);

	/**
	 * 弹出最左边的元素，弹出之后该值在列表中将不复存在
	 * @param key
	 * @return
	 */
	public Object listLeftPop(String key);


	/**
	 * 移出并获取列表的第一个元素， 如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止。
	 * @param key
	 * @param timeout
	 * @param unit
	 * @return
	 */
	public Object listLeftPop(String key, long timeout, TimeUnit unit);

	/**
	 * 弹出最右边的元素，弹出之后该值在列表中将不复存在
	 * @param key
	 * @return
	 */
	public Object listRightPop(String key);

	/**
	 * 移出并获取列表的最后一个元素， 如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止。
	 * @param key
	 * @param timeout
	 * @param unit
	 * @return
	 */
	public Object listRightPop(String key, long timeout, TimeUnit unit);

	/**
	 * 用于移除列表的最后一个元素，并将该元素添加到另一个列表并返回。
	 * @param sourceKey 原列表的键
	 * @param destinationKey 目标列表的键
	 * @return
	 */
	public Object listRightPopAndLeftPush(String sourceKey, String destinationKey);

	/**
	 * 用于移除列表的最后一个元素，并将该元素添加到另一个列表并返回，如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止。
	 * @param sourceKey
	 * @param destinationKey
	 * @param timeout
	 * @param unit
	 * @return
	 */
	public Object listRightPopAndLeftPush(String sourceKey, String destinationKey,  long timeout, TimeUnit unit);

	/**
	 * 删除给定的哈希hashKeys
	 * @param key 键
	 * @param hashKeys 要删除的值
	 * @return
	 */
	public Long hashDelete(String key, Object... hashKeys);

	/**
	 * 确定哈希hashKey是否存在
	 * @param key
	 * @param hashKey
	 * @return
	 */
	public Boolean  hashHasKey(String key, Object hashKey);

	/**
	 * 从键中的哈希获取给定hashKey的值
	 * @param key
	 * @param hashKey
	 */
	public Object hashGet(String key, Object hashKey);

	/**
	 * 从哈希中获取给定hashKey的值
	 * List<Object> kes = new ArrayList<Object>();
	 * kes.add("name");
	 * kes.add("age");
	 * System.out.println(template.opsForHash().multiGet("redisHash",kes));
	 * @param key
	 * @param hashKeys
	 * @return
	 */
	public <T> List<T> hashMultiGet(String key, Collection<Object> hashKeys);

	/**
	 * 通过给定的delta增加散列hashKey的值（整型）
	 * increment("redisHash","age",1)
	 * @param key
	 * @param hashKey
	 * @param delta
	 * @return
	 */
	public Long hashIncrement(String key, Object hashKey, long delta);

	/**
	 * 通过给定的delta增加散列hashKey的值（浮点数）
	 * increment("redisHash","age",1)
	 * @param key
	 * @param hashKey
	 * @param delta
	 * @return
	 */
	public Double hashIncrement(String key, Object hashKey, double delta);

	/**
	 *  获取key所对应的散列表的key
	 * @param key
	 * @return
	 */
	public Set<Object> hashKeys(String key);

	/**
	 * 获取key所对应的散列表的大小个数
	 * @param key
	 * @return
	 */
	public Long hashSize(String key);

	/**
	 * 使用中提供的多个散列字段设置到key对应的散列表中
	 * @param key
	 * @param m
	 */
	public void hashPutAll(String key, Map<Object, Object> map);

	/**
	 * 设置散列hashKey的值
	 * @param key
	 * @param hashKey
	 * @param value
	 */
	public void hashPut(String key, Object hashKey, Object value);

	/**
	 * 仅当hashKey不存在时才设置散列hashKey的值。
	 * @param key
	 * @param hashKey
	 * @param value
	 * @return
	 */
	public Boolean hashPutIfAbsent(String key, Object hashKey, Object value);

	/**
	 * 获取整个哈希存储的值根据密钥
	 * @param key
	 * @return
	 */
	public <T> List<T> hashValues(String key);

	/**
	 * 获取整个哈希存储根据密钥
	 * @param key
	 * @return
	 */
	public Map<Object, Object> hashEntries(String key);

	/**
	 *  无序集合中添加元素，返回添加个数
	 *   也可以直接在add里面添加多个值 如：template.opsForSet().add("setTest","aaa","bbb")
	 * @param key
	 * @param values
	 * @return
	 */
	public Long setAdd(String key, Object ... values);

	/**
	 * 移除集合中一个或多个成员
	 * @param key
	 * @param values
	 * @return
	 */
	public Long setRemove(String key, Object... values);

	/**
	 * 移除并返回集合中的一个随机元素
	 * @param key
	 * @return
	 */
	public Object setPop(String key);

	/**
	 *  将 member 元素从 source 集合移动到 destination 集合
	 * @param key
	 * @param value
	 * @param destKey
	 * @return
	 */
	public Boolean setMove(String key, Object value, String destKey);

	/**
	 * 无序集合的大小长度
	 * @param key
	 * @return
	 */
	public Long setSize(String key);

	/**
	 * 判断 member 元素是否是集合 key 的成员
	 * @param key
	 * @param obj
	 * @return
	 */
	public Boolean setIsMember(String key, Object obj);

	/**
	 * key对应的无序集合与otherKey对应的无序集合求交集
	 * @param key
	 * @param otherKey
	 * @return
	 */
	public Set<Object> setIntersect(String key, String otherKey);

	/**
	 * key对应的无序集合与多个otherKey对应的无序集合求交集
	 * @param key
	 * @param otherKeys
	 * @return
	 */
	public Set<Object> setIntersect(String key, Collection<String> otherKeys);

	/**
	 * key无序集合与otherkey无序集合的交集存储到destKey无序集合中
	 * @param key
	 * @param otherKey
	 * @param destKey
	 * @return
	 */
	public Long setIntersectAndStore(String key, String otherKey, String destKey);

	/**
	 * key对应的无序集合与多个otherKey对应的无序集合求交集存储到destKey无序集合中
	 * @param key
	 * @param otherKeys
	 * @param destKey
	 * @return
	 */
	public Long setIntersectAndStore(String key, Collection<String> otherKeys, String destKey);

	/**
	 * key无序集合与otherKey无序集合的并集
	 * @param key
	 * @param otherKey
	 * @return
	 */
	public Set<Object> setUnion(String key, String otherKey);
	/**
	 * key无序集合与多个otherKey无序集合的并集
	 * @param key
	 * @param otherKeys
	 * @return
	 */
	public Set<Object> setUnion(String key, Collection<String> otherKeys);

	/**
	 * key无序集合与otherkey无序集合的并集存储到destKey无序集合中
	 * @param key
	 * @param otherKey
	 * @param destKey
	 * @return
	 */
	public Long setUnionAndStore(String key, String otherKey, String destKey);

	/**
	 * key无序集合与多个otherkey无序集合的并集存储到destKey无序集合中
	 * @param key
	 * @param otherKeys
	 * @param destKey
	 * @return
	 */
	public Long unionAndStore(String key, Collection<String> otherKeys, String destKey);

	/**
	 * key无序集合与otherKey无序集合的差集
	 * @param key
	 * @param otherKey
	 * @return
	 */
	public Set<Object> setDifference(String key, String otherKey);

	/**
	 * key无序集合与多个otherKey无序集合的差集
	 * @param key
	 * @param otherKeys
	 * @return
	 */
	public Set<Object> setDifference(String key, Collection<String> otherKeys);

	/**
	 * key无序集合与otherkey无序集合的差集存储到destKey无序集合中
	 * @param key
	 * @param otherKey
	 * @param destKey
	 * @return
	 */
	public Long setDifferenceAndStore(String key, String otherKey, String destKey);

	/**
	 * key无序集合与多个otherkey无序集合的差集存储到destKey无序集合中
	 * @param key
	 * @param otherKeys
	 * @param destKey
	 * @return
	 */
	public Long setDifferenceAndStore(String key, Collection<String> otherKeys, String destKey);

	/**
	 * 返回集合中的所有成员
	 * @param key
	 * @return
	 */
	public Set<Object> setMembers(String key);

	/**
	 * 随机获取key无序集合中的一个元素
	 * @param key
	 * @return
	 */
	public Object setRandomMember(String key) ;

	/**
	 * 获取多个key无序集合中的元素（去重），count表示个数
	 * @param key
	 * @param count
	 * @return
	 */
	public Set<Object> setDistinctRandomMembers(String key, long count);
	/**
	 * 获取多个key无序集合中的元素，count表示个数
	 * @param key
	 * @param count
	 * @return
	 */
	public <T> List<T> setRandomMembers(String key, long count);
	
	/**
	 * 删除键
	 * @param key
	 */
	public Boolean delete(String key);
	
	public Long delete(Collection<String> keys);
	
	/**
	 * 新增一个有序集合
	 * ZSetOperations.TypedTuple<Object> objectTypedTuple1 = new DefaultTypedTuple<Object>("zset-5",9.6);
	 * ZSetOperations.TypedTuple<Object> objectTypedTuple2 = new DefaultTypedTuple<Object>("zset-6",9.9);
	 * Set<ZSetOperations.TypedTuple<Object>> tuples = new HashSet<ZSetOperations.TypedTuple<Object>>();
	 * tuples.add(objectTypedTuple1);
	 * tuples.add(objectTypedTuple2);
	 * @param key
	 * @param tuples
	 * @return
	 */
	public Long zsetAdd(String key, Set<TypedTuple<Object>> tuples);

	/**
	 * 从有序集合中移除一个或者多个元素
	 * @param key
	 * @param values
	 * @return
	 */
	public Long zsetRemove(String key, Object... values);

	/**
	 * 增加元素的score值，并返回增加后的值
	 * @param key
	 * @param value
	 * @param delta
	 * @return
	 */
	public Double zsetIncrementScore(String key, Object value, double delta);

	/**
	 * 返回有序集中指定成员的排名，其中有序集成员按分数值递增(从小到大)顺序排列
	 * @param key
	 * @param o
	 * @return
	 */
	public Long zsetRank(String key, Object o);

	/**
	 * 返回有序集中指定成员的排名，其中有序集成员按分数值递减(从大到小)顺序排列
	 * @param key
	 * @param o
	 * @return
	 */
	public Long zsetReverseRank(String key, Object o);

	/**
	 * 通过索引区间返回有序集合成指定区间内的成员，其中有序集成员按分数值递增(从小到大)顺序排列
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	public Set<Object> zsetRange(String key, long start, long end);

	/**
	 * 通过索引区间返回有序集合成指定区间内的成员对象，其中有序集成员按分数值递增(从小到大)顺序排列
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	public Set<TypedTuple<Object>> zsetRangeWithScores(String key, long start, long end);

	/**
	 *  通过分数返回有序集合指定区间内的成员，其中有序集成员按分数值递增(从小到大)顺序排列
	 * @param key
	 * @param min
	 * @param max
	 * @return
	 */
	public Set<Object> zsetRangeByScore(String key, double min, double max);

	/**
	 * 通过分数返回有序集合指定区间内的成员对象，其中有序集成员按分数值递增(从小到大)顺序排列
	 * @param key
	 * @param min
	 * @param max
	 * @return
	 */
	public Set<TypedTuple<Object>> zsetRangeByScoreWithScores(String key, double min, double max);

	/**
	 * 通过分数返回有序集合指定区间内的成员，并在索引范围内，其中有序集成员按分数值递增(从小到大)顺序排列
	 * @param key
	 * @param min
	 * @param max
	 * @param offset
	 * @param count
	 * @return
	 */
	public Set<Object> zsetRangeByScore(String key, double min, double max, long offset, long count);

	/**
	 * 通过分数返回有序集合指定区间内的成员对象，并在索引范围内，其中有序集成员按分数值递增(从小到大)顺序排列
	 * @param key
	 * @param min
	 * @param max
	 * @param offset
	 * @param count
	 * @return
	 */
	public Set<TypedTuple<Object>> zsetRangeByScoreWithScores(String key, double min, double max, long offset, long count);

	/**
	 * 通过索引区间返回有序集合成指定区间内的成员，其中有序集成员按分数值递减(从大到小)顺序排列
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	public Set<Object> zsetR(String key, long start, long end);

	/**
	 * 通过索引区间返回有序集合成指定区间内的成员对象，其中有序集成员按分数值递减(从大到小)顺序排列
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	public  Set<TypedTuple<Object>> zsetReverseRangeWithScores(String key, long start, long end);

	/**
	 * 与rangeByScore调用方法一样，其中有序集成员按分数值递减(从大到小)顺序排列
	 * @param key
	 * @param min
	 * @param max
	 * @return
	 */
	public Set<Object> rzsetReverseRangeByScore(String key, double min, double max);

	/**
	 * 与rangeByScoreWithScores调用方法一样，其中有序集成员按分数值递减(从大到小)顺序排列
	 * @param key
	 * @param min
	 * @param max
	 * @return
	 */
	public Set<TypedTuple<Object>> zsetReverseRangeByScoreWithScores(String key, double min, double max);

	/**
	 * 与rangeByScore调用方法一样，其中有序集成员按分数值递减(从大到小)顺序排列
	 * @param key
	 * @param min
	 * @param max
	 * @param offset
	 * @param count
	 * @return
	 */
	public Set<Object> zsetReverseRangeByScore(String key, double min, double max, long offset, long count);

	/**
	 * 与rangeByScoreWithScores调用方法一样，其中有序集成员按分数值递减(从大到小)顺序排列
	 * @param key
	 * @param min
	 * @param max
	 * @param offset
	 * @param count
	 * @return
	 */
	public Set<TypedTuple<Object>> zsetReverseRangeByScoreWithScores(String key, double min, double max, long offset, long count);

	/**
	 * 通过分数返回有序集合指定区间内的成员个数
	 * @param key
	 * @param min
	 * @param max
	 * @return
	 */
	public Long zsetCount(String key, double min, double max);

	/**
	 * 获取有序集合的成员数，内部调用的就是zCard方法
	 */
	public Long zsetSize(String key);

	/**
	 * 获取有序集合的成员数
	 * @param key
	 * @return
	 */
	public Long zsetZCard(String key);

	/**
	 * 获取指定成员的score值
	 * @param key
	 * @param o
	 * @return
	 */
	public Double zsetScore(String key, Object o);

	/**
	 * 移除指定索引位置的成员，其中有序集成员按分数值递增(从小到大)顺序排列
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	public Long zsetRemoveRange(String key, long start, long end);

	/**
	 * 根据指定的score值得范围来移除成员
	 * @param key
	 * @param min
	 * @param max
	 * @return
	 */
	public Long zsetRemoveRangeByScore(String key, double min, double max);

	/**
	 * 计算给定的一个有序集的并集，并存储在新的 destKey中，key相同的话会把score值相加
	 * @param key
	 * @param otherKey
	 * @param destKey
	 * @return
	 */
	public Long unionAndStore(String key, String otherKey, String destKey);

	/**
	 * 计算给定的多个有序集的并集，并存储在新的 destKey中
	 * @param key
	 * @param otherKeys
	 * @param destKey
	 * @return
	 */
	public Long zsetUnionAndStore(String key, Collection<String> otherKeys, String destKey);

	/**
	 * 计算给定的一个或多个有序集的交集并将结果集存储在新的有序集合 key 中
	 * @param key
	 * @param otherKey
	 * @param destKey
	 * @return
	 */
	public Long zsetIntersectAndStore(String key, String otherKey, String destKey);


	/**
	 * 计算给定的一个或多个有序集的交集并将结果集存储在新的有序集合 key 中
	 * @param key
	 * @param otherKeys
	 * @param destKey
	 * @return
	 */
	public Long zsetIntersectAndStore(String key, Collection<String> otherKeys, String destKey);

	/**
	 * 遍历zset
	 * @param key
	 * @param options
	 * @return
	 */
	public Cursor<TypedTuple<Object>> zsetScan(String key, ScanOptions options);

	/**
	 * 遍历set
	 * Cursor<Object> curosr = template.opsForSet().scan("setTest", ScanOptions.NONE);
	 * while(curosr.hasNext()){
	 *     System.out.println(curosr.next());
	 * }
	 * @param key
	 * @param options
	 * @return
	 */
	public Cursor<Object> setScan(String key, ScanOptions options);

	/**
	 * 使用Cursor在key的hash中迭代，相当于迭代器。
	 * Cursor<Map.Entry<Object, Object>> curosr = template.opsForHash().scan("redisHash", ScanOptions.ScanOptions.NONE);
	 * while(curosr.hasNext()){
	 *     Map.Entry<Object, Object> entry = curosr.next();
	 *     System.out.println(entry.getKey()+":"+entry.getValue());
	 * }
	 * @param key
	 * @param options
	 * @return
	 */
	public Cursor<Map.Entry<Object, Object>> hashScan(String key, ScanOptions options);

	/**
	 * 对 key 所储存的字符串值，设置或清除指定偏移量上的位(bit) key键对应的值value对应的ascii码,在offset的位置(从左向右数)变为value
	 * opsForValue().set("bitTest","a");
	 * 'a' 的ASCII码是 97。转换为二进制是：01100001
	 * 'b' 的ASCII码是 98  转换为二进制是：01100010
	 * 'c' 的ASCII码是 99  转换为二进制是：01100011
	 * 因为二进制只有0和1，在setbit中true为1，false为0，因此我要变为'b'的话第六位设置为1，第七位设置为0
	 * opsForValue().setBit("bitTest",6, true);
	 * opsForValue().setBit("bitTest",7, false);
	 * opsForValue().get("bitTest"); // b
	 * @param key
	 * @param offset
	 * @param value
	 * @return
	 */
	public Boolean setBit(String key, long offset, boolean value);

	/**
	 * 获取键对应值的ascii码的在offset处位值
	 */
	public Boolean getBit(String key, long offset);
}
