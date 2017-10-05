# Реализуйте класс, аналогичный Array, с многопоточной 
# реализацией итераторов: map, any?, all?, select. Объясните, 
# можно ли таким образом реализовать итератор inject?
# Ограничения:
# - не допускается использовать циклы;
# - вся логика работы с потоками должны быть вынесена в 
# отдельный метод, общий для всех итераторов.

class MyArray < Array 
    def pmap(thr_num, &block)
        threads = self.each_slice(thr_num)
            .flat_map { |arr| arr.map { |elem| Thread.new { block.call(elem) } } }
        threads.map { |thread| thread.value }
    end  

    def pany?(thr_num, &block)
        arr = pmap(thr_num, &block)
        arr.any?
    end

    def pall?(thr_num, &block)
        arr = pmap(thr_num, &block)
        arr.all?
    end

    def pselect(thr_num, &block)
        arr = pmap(thr_num) { |elem| [block.call(elem), elem] }
        filtered_arr = arr.select { |pair| pair[0] }
        filtered_arr.map { |pair| pair[1] }
    end
end

a = MyArray.new(6) { |i| i + 1 }
puts (a.pmap(4) { |i| i + 2 } ).inspect

is_even = lambda { |num| num.even? }
puts a.pany?(4, &is_even).inspect
puts a.pall?(4, &is_even).inspect
puts a.pselect(4, &is_even).inspect
