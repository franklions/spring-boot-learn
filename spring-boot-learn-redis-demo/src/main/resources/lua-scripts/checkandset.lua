 -- checkandset.lua local
local current = redis.call('GET', KEYS[1])
 print(KEYS[1])
 print(ARGV[1])
 print("abcdefs")
 if current == ARGV[1]
 then
     redis.call('SET', KEYS[1], ARGV[2])
     return true
 end
 return false
