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
    generate_next = lambda do |arr| 
        alphabet.select { |letter| letter != arr[-1] }.map { |letter| arr + [letter] } 
    end
    (1..n).reduce([[]]) { |memo| memo.flat_map &generate_next }
end

n = 2
alphabet = [2, 3, 6]

generate_without_doubles(n, alphabet).each { |arr| puts arr.inspect }
