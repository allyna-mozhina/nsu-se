# Реализуйте класс, аналогичный Array, с многопоточной 
# реализацией итераторов: map, any?, all?, select. Объясните, 
# можно ли таким образом реализовать итератор inject?
# Ограничения:
# - не допускается использовать циклы;
# - вся логика работы с потоками должны быть вынесена в 
# отдельный метод, общий для всех итераторов.

require 'thread'

class Array 
    def pmap(thr_num, slice_size = 2, &block)
        queue = Queue.new
        i = 0

        self.each_slice(slice_size).map { |slice| queue << [i, slice]; i += 1 }

        result = Array.new(i)
        proc_queue = lambda do
            until queue.empty?
                begin
                    pair = queue.pop(true) 
                rescue
                    return                    
                end             

                result_slice = pair[1].map { |elem| block.call(elem) }
                result[pair[0]] = result_slice
            end                
        end

        threads = (1..thr_num).map { |_| Thread.new(&proc_queue) }
        threads.map { |thread| thread.join }
        
        result.flatten(1)
    end

    def pany?(thr_num, slice_size = 2, &block)
        arr = pmap(thr_num, slice_size, &block)
        arr.any?
    end

    def pall?(thr_num, slice_size = 2, &block)
        arr = pmap(thr_num, slice_size, &block)
        arr.all?
    end

    def pselect(thr_num, slice_size = 2, &block)
        arr = pmap(thr_num, slice_size) { |elem| [block.call(elem), elem] }
        filtered_arr = arr.select { |pair| pair[0] }
        filtered_arr.map { |pair| pair[1] }
    end
end

a = Array.new(6) { |i| i + 1 }
puts (a.pmap(4, 2) { |i| i + 2 } ).inspect

is_even = lambda { |num| num.even? }
puts a.pany?(4, &is_even).inspect
puts a.pall?(4, &is_even).inspect
puts a.pselect(4, &is_even).inspect
