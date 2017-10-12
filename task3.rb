# Вариант 1:
# Написать программу, разбирающую журнал событий (например, 
# журнал web-сервера, syslog и т.д.) и вычисляющий ежемесячную 
# статистику в зависимости от содержимого выбранного журнала 
# (например, обращения с различных IP-адресов, ошибки различных 
# типов, аутентификацию пользователей и т.д.)

# Вариант 2:
# По детализации звонков (предварительно преобразованным в 
# текстовый формат) своего сотового оператора за несколько месяцев 
# сделать расчет финансовых затрат по нескольким различным тарифам. 
# Допускается использовать не все параметры тарифа, только наиболее 
# важные (например, абонентская плата, стоимость звонков, 
# дифференцированная по различным видам номеров).

# Вариант 3:
# Предложите свою постановку задачи и обсудите ее с преподавателем. 
# При решении должны быть использованы регулярные выражения с 3-4 
# нетривиальными группами.

# Ограничения
# Необходимо использовать расширенные рег. выражения с комментариями

require 'set'

rx = /^
     ([\w\.\/,-]+)\s                                   #host
     -\s-\s
     (\[\d\d\/\w+\/\d\d\d\d:\d\d:\d\d:\d\d\s-0400\])\s #timestamp
     ("(GET|HEAD|POST)\s[\s\/\w\.\*,_?+~%:#=><-]+")\s  #request
     (\d+)\s                                           #reply code
     (\d+|-)                                           #reply bytes
     $/x

unique_hosts = Set.new
success_count = 0

File.open("NASA_Aug95.log").each do |line|
    md = line.match(rx)

    if md == nil       
        puts "Failed match:" + line
        next
    end

    unique_hosts.add?(md[1])

    if md[5].start_with?('2')
        success_count += 1
    end
end

puts "Unique hosts: #{unique_hosts.size}"
puts "Return code 2xx: #{success_count}"