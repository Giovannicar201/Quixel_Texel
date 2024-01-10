package QuixelTexel.FIA.Utility;

import org.jgrapht.Graph;
import org.jgrapht.alg.interfaces.SpanningTreeAlgorithm;
import org.jgrapht.alg.spanning.KruskalMinimumSpanningTree;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import QuixelTexel.FIA.Entity.ArcoEntity;
import java.util.ArrayList;
import java.util.List;

public class Kruskal {

    /**
     * Crea un grafo completamente connesso e ne calcola il maximum spanning tree.
     *
     * @param V Lista di vertici usata per comporre il grafo.
     * @param E Lista di archi usata per comporre il grafo.
     * @return  Somma dei pesi degli archi che compongono il maximum spanning tree.
     * @author Giovanni Carbone
     */
    public static float computaMaxSTCompletamenteConnesso(List<String> V, List<ArcoEntity> E) {

        Graph<String, DefaultWeightedEdge> grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);

        for (String v : V)
            grafo.addVertex(v);

        for (ArcoEntity arco : E) {
            String v = arco.getV();
            String w = arco.getW();

            double peso = arco.getPeso();

            grafo.addEdge(v, w);
            grafo.setEdgeWeight(grafo.getEdge(v, w), peso);
        }

        KruskalMinimumSpanningTree<String, DefaultWeightedEdge> kruskalMST = new KruskalMinimumSpanningTree<>(grafo);

        List<DefaultWeightedEdge> mstEdges = new ArrayList<>(kruskalMST.getSpanningTree().getEdges());

        List<DefaultWeightedEdge> maxSTEdges = new ArrayList<>();

        for (DefaultWeightedEdge arco : grafo.edgeSet())
            if (!mstEdges.contains(arco))
                maxSTEdges.add(arco);

        double pesoTotale = 0.0;

        for (DefaultWeightedEdge arco : maxSTEdges)
            pesoTotale += grafo.getEdgeWeight(arco);

        return (float) pesoTotale;
    }

    /**
     * Crea un grafo completamente connesso e ne calcola il minimum spanning tree.
     *
     * @param V Lista di vertici usata per comporre il grafo.
     * @param E Lista di archi usata per comporre il grafo.
     * @return  Somma dei pesi degli archi che compongono il minimum spanning tree.
     * @author Giovanni Carbone
     */
    public static float computaMinSTCompletamenteConnesso(List<String> V, List<ArcoEntity> E) {

        Graph<String, DefaultWeightedEdge> grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);

        for (String v : V)
            grafo.addVertex(v);

        for (ArcoEntity arco : E) {
            String v = arco.getV();
            String w = arco.getW();

            double peso = arco.getPeso();

            grafo.addEdge(v,w);
            grafo.setEdgeWeight(grafo.getEdge(v,w), peso);
        }

        KruskalMinimumSpanningTree<String, DefaultWeightedEdge> kruskalMST = new KruskalMinimumSpanningTree<>(grafo);

        SpanningTreeAlgorithm.SpanningTree<DefaultWeightedEdge> mst = kruskalMST.getSpanningTree();

        double pesoTotale = 0.0;

        for (DefaultWeightedEdge arco : mst.getEdges())
            pesoTotale += grafo.getEdgeWeight(arco);

        return (float) pesoTotale;
    }
}
