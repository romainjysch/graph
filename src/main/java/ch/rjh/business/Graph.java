package ch.rjh.business;

import java.util.*;

public class Graph {

    // Attributes :
    private String name;
    private Map<String, Node> nodeMap;

    // Constructors :
    public Graph(String name) {
        this.name = name;
        nodeMap = new HashMap<>();
    }

    // Getter & Setter :
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, Node> getNodeMap() {
        return nodeMap;
    }

    public void setNodeMap(Map<String, Node> nodeMap) {
        this.nodeMap = nodeMap;
    }

    // Methods :
    public Node findNode(String nodeName) {
        if (nodeName.contains(nodeName)) {
            return nodeMap.get(nodeName);
        }
        else {
            return null;
        }
    }

    public Edge findEdge(String nodeName, String edgeName) {
        if (nodeMap.get(nodeName).getExitingEdge().containsKey(edgeName)) {
            return nodeMap.get(nodeName).getExitingEdge().get(edgeName);
        }
        else {
            return null;
        }
    }

    public void listEnteringEdge(String nodeName) {
        List<Edge> list = new ArrayList<>();
        Node node = findNode(nodeName);
        for (Edge edge : node.getEnteringEdge().values()) {
            list.add(edge);
        }
        System.out.println("Arc entrants de " + nodeName + " :");
        System.out.println(list);
    }

    public void removeNode(String nodeName) {
        for (Node node : this.nodeMap.values()) {
            Iterator<Edge> iteEdge = node.getExitingEdge().values().iterator();
            while (iteEdge.hasNext()) {
                Edge edge = iteEdge.next();
                if (nodeName == edge.getDestination().getName()) {
                    iteEdge.remove();
                }
            }
        }
        nodeMap.remove(nodeName);
    }

    public void removeNodeWithSource(String nodeName) {
        nodeMap.remove(nodeName);
    }

    public void addEdge(String nodeSource, String nodeDestination, double metric, String edgeName) {
        if (findNode(nodeSource) == null ) {
            Node node = new Node(nodeSource);
            nodeMap.put(node.getName(), node);
        }
        if (findNode(nodeDestination) == null) {
            Node node = new Node(nodeDestination);
            nodeMap.put(node.getName(), node);
        }
        Edge edge = new Edge(edgeName, metric, findNode(nodeDestination));
        findNode(nodeSource).getExitingEdge().put(edge.getName(), edge);
    }

    public void addEdgeWithSource(String nodeSource, String nodeDestination, double metric, String edgeName) {
        if (findNode(nodeSource) == null ) {
            Node node = new Node(nodeSource);
            nodeMap.put(node.getName(), node);
        }
        if (findNode(nodeDestination) == null) {
            Node node = new Node(nodeDestination);
            nodeMap.put(node.getName(), node);
        }
        Edge edge = new Edge(edgeName, metric, findNode(nodeSource), findNode(nodeDestination));
        findNode(nodeSource).getExitingEdge().put(edge.getName(), edge);
        findNode(nodeDestination).getEnteringEdge().put(edge.getName(), edge);
    }

    public void removeEdge(String nodeName, String edgeName) {
        findNode(nodeName).getExitingEdge().remove(edgeName);
    }

    public void removeEdgeWithSource(String nodeSource, String nodeDestination, String edgeName) {
        findNode(nodeSource).getExitingEdge().remove(edgeName);
        findNode(nodeDestination).getEnteringEdge().remove(edgeName);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n").append("Graph ").append(name).append(" :\n");
        for (Map.Entry<String, Node> entryNode : nodeMap.entrySet()) {
            sb.append("w++(").append(entryNode.getKey()).append(") = { ");
            for (Map.Entry<String, Edge> entryEdge : entryNode.getValue().getExitingEdge().entrySet()) {
                sb.append(entryEdge.getKey())
                        .append("(")
                        .append(entryNode.getKey())
                        .append(",")
                        .append(entryEdge.getValue().getDestination().getName())
                        .append(",")
                        .append(String.valueOf(entryEdge.getValue().getMetric()))
                        .append(") ");
            }
            sb.append("}\n");
        }
        return sb.toString();
    }

}
