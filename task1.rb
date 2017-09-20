# Задан набор символов и число n. Опишите
# функцию, которая возвращает список всех строк
# длины n, состоящих из этих символов и не
# содержащих двух одинаковых символов, идущих
# подряд.
# Ограничения:
# - не допускается использовать циклы
# Пример: для символов 'а', 'b', 'c' и n=2 результат
# должен быть ("ab" "ac" "ba" "bc" "ca" "cb") с
# точностью до перестановки.

def generate_without_doubles(n, alphabet)
    generate_next = lambda do |str| 
        alphabet.select { |letter| letter != str[-1] }.map { |letter| str + letter } 
    end
    (1..n).reduce(['']) { |memo| (memo.map &generate_next).reduce { |memo, arr| memo + arr } }
end

n = 2
alphabet = ['a', 'b', 'c']

generate_without_doubles(n, alphabet).each { |str| puts str }
