package lesson6;

import kotlin.NotImplementedError;

import java.util.*;

@SuppressWarnings("unused")
public class JavaGraphTasks {
    /**
     * Эйлеров цикл.
     * Средняя
     *
     * Дан граф (получатель). Найти по нему любой Эйлеров цикл.
     * Если в графе нет Эйлеровых циклов, вернуть пустой список.
     * Соседние дуги в списке-результате должны быть инцидентны друг другу,
     * а первая дуга в списке инцидентна последней.
     * Длина списка, если он не пуст, должна быть равна количеству дуг в графе.
     * Веса дуг никак не учитываются.
     *
     * Пример:
     *
     *      G -- H
     *      |    |
     * A -- B -- C -- D
     * |    |    |    |
     * E    F -- I    |
     * |              |
     * J ------------ K
     *
     * Вариант ответа: A, E, J, K, D, C, H, G, B, C, I, F, B, A
     *
     * Справка: Эйлеров цикл -- это цикл, проходящий через все рёбра
     * связного графа ровно по одному разу
     */

    //Трудоемкость O(m * n), где n - кол-во вершин, m - кол-во ребер
    //Ресурсоемкость O(m * n)
    public static List<Graph.Edge> findEulerLoop(Graph graph) {
        LinkedList<Graph.Edge> allEdges = new LinkedList<>(graph.getEdges()); //чтобы убирать ребро, которое прошли
        LinkedList<Graph.Vertex> allVertices = new LinkedList<>(graph.getVertices());
        //прверка графа
        if (allVertices.isEmpty() || allEdges.isEmpty()) return Collections.emptyList();

        //проверка на эйлерность
        int odd = 0;
        for (Graph.Vertex v:
             graph.getVertices()) {
            if (graph.getNeighbors(v).size() % 2 != 0) {
                odd++;
            }
        }
        if (odd > 0) {
            return Collections.emptyList();
        }

        ArrayDeque<Graph.Vertex> result = new ArrayDeque<>();
        ArrayDeque<Graph.Vertex> vertices = new ArrayDeque<>();
        vertices.push(allVertices.getFirst());
        while (!vertices.isEmpty()) {
            Graph.Vertex start = vertices.getFirst();
            Set<Graph.Vertex> nextVertexes = graph.getNeighbors(start);
            int deg = 0;
            for (Graph.Vertex v :
                    nextVertexes) {
                Graph.Edge edge = graph.getConnection(start, v);
                if (edge != null && allEdges.contains(edge)) {
                    deg++;
                    allEdges.remove(edge);
                    vertices.push(v);
                    break;
                }
            }
            if (deg == 0) {
                vertices.pop();
                result.add(start);
            }
        }

        List<Graph.Edge> answer = new ArrayList<>();
        int limit = result.size() - 1;
        for (int i = 0; i < limit; i++) {
            Graph.Vertex starting = result.poll();
            Graph.Vertex ending = result.getFirst();
            assert starting != null;
            answer.add(graph.getConnection(starting,ending));
        }
        return answer;
    }

    /**
     * Минимальное остовное дерево.
     * Средняя
     *
     * Дан связный граф (получатель). Найти по нему минимальное остовное дерево.
     * Если есть несколько минимальных остовных деревьев с одинаковым числом дуг,
     * вернуть любое из них. Веса дуг не учитывать.
     *
     * Пример:
     *
     *      G -- H
     *      |    |
     * A -- B -- C -- D
     * |    |    |    |
     * E    F -- I    |
     * |              |
     * J ------------ K
     *
     * Ответ:
     *
     *      G    H
     *      |    |
     * A -- B -- C -- D
     * |    |    |
     * E    F    I
     * |
     * J ------------ K
     */
    public static Graph minimumSpanningTree(Graph graph) {
        throw new NotImplementedError();
    }

    /**
     * Максимальное независимое множество вершин в графе без циклов.
     * Сложная
     *
     * Дан граф без циклов (получатель), например
     *
     *      G -- H -- J
     *      |
     * A -- B -- D
     * |         |
     * C -- F    I
     * |
     * E
     *
     * Найти в нём самое большое независимое множество вершин и вернуть его.
     * Никакая пара вершин в независимом множестве не должна быть связана ребром.
     *
     * Если самых больших множеств несколько, приоритет имеет то из них,
     * в котором вершины расположены раньше во множестве this.vertices (начиная с первых).
     *
     * В данном случае ответ (A, E, F, D, G, J)
     *
     * Если на входе граф с циклами, бросить IllegalArgumentException
     */

    //Трудоемкость O(2^n), где n - кол-во вершин
    //Ресурсоемкость O(2^n)
    public static Set<Graph.Vertex> largestIndependentVertexSet(Graph graph) {
        Set<Graph.Vertex> answer = new HashSet<>();
        ArrayDeque<Graph.Vertex> allVertices = new ArrayDeque<>(graph.getVertices());
        if(allVertices.isEmpty()) return Collections.emptySet();
        LinkedList<Graph.Vertex> verticesExist = new LinkedList<>(graph.getVertices());
        Graph.Vertex start = allVertices.getFirst();
        answer.add(start);
        while (!verticesExist.isEmpty()) {
            verticesExist.remove(start);
            for (Graph.Vertex v : allVertices) {
                if (verticesExist.contains(v)) {
                    Set<Graph.Vertex> neighbours = graph.getNeighbors(start);
                    verticesExist.removeAll(neighbours);
                    Graph.Edge connection = graph.getConnection(start, v);
                    if (connection == null) {
                        verticesExist.remove(v);
                        answer.add(v);
                        start = v;
                        break;
                    }
                }
            }
        }
        return answer;
    }

    /**
     * Наидлиннейший простой путь.
     * Сложная
     *
     * Дан граф (получатель). Найти в нём простой путь, включающий максимальное количество рёбер.
     * Простым считается путь, вершины в котором не повторяются.
     * Если таких путей несколько, вернуть любой из них.
     *
     * Пример:
     *
     *      G -- H
     *      |    |
     * A -- B -- C -- D
     * |    |    |    |
     * E    F -- I    |
     * |              |
     * J ------------ K
     *
     * Ответ: A, E, J, K, D, C, H, G, B, F, I
     */
    public static Path longestSimplePath(Graph graph) {
        throw new NotImplementedError();
    }


    /**
     * Балда
     * Сложная
     *
     * Задача хоть и не использует граф напрямую, но решение базируется на тех же алгоритмах -
     * поэтому задача присутствует в этом разделе
     *
     * В файле с именем inputName задана матрица из букв в следующем формате
     * (отдельные буквы в ряду разделены пробелами):
     *
     * И Т Ы Н
     * К Р А Н
     * А К В А
     *
     * В аргументе words содержится множество слов для поиска, например,
     * ТРАВА, КРАН, АКВА, НАРТЫ, РАК.
     *
     * Попытаться найти каждое из слов в матрице букв, используя правила игры БАЛДА,
     * и вернуть множество найденных слов. В данном случае:
     * ТРАВА, КРАН, АКВА, НАРТЫ
     *
     * И т Ы Н     И т ы Н
     * К р а Н     К р а н
     * А К в а     А К В А
     *
     * Все слова и буквы -- русские или английские, прописные.
     * В файле буквы разделены пробелами, строки -- переносами строк.
     * Остальные символы ни в файле, ни в словах не допускаются.
     */
    static public Set<String> baldaSearcher(String inputName, Set<String> words) {
        throw new NotImplementedError();
    }
}
