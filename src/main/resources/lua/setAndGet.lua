--import redis
local key = KEYS[1]
local args = ARGV
redis.call("SET", key, unpack(args))
return redis.call("GET", key)