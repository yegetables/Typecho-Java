--import redis
local key = KEYS[1]
--local args = ARGV
return redis.call("GET", redis.call("GET", key))