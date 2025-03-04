package org.jmantic.scmemory.model;

import org.jmantic.scmemory.model.element.ScElement;
import org.jmantic.scmemory.model.element.edge.EdgeType;
import org.jmantic.scmemory.model.element.edge.ScEdge;
import org.jmantic.scmemory.model.element.link.LinkContentType;
import org.jmantic.scmemory.model.element.link.LinkType;
import org.jmantic.scmemory.model.element.link.ScLinkFloat;
import org.jmantic.scmemory.model.element.link.ScLinkInteger;
import org.jmantic.scmemory.model.element.link.ScLinkString;
import org.jmantic.scmemory.model.element.node.NodeType;
import org.jmantic.scmemory.model.element.node.ScNode;
import org.jmantic.scmemory.model.exception.ScMemoryException;

import java.util.stream.Stream;

/**
 * This interface represents global contract between java-code and the sc-machine
 * <p>
 * ToDo:
 * <ul>
 *     <li>Elements generator by template</li>
 *     <li>More searching templates</li>
 *     <li>Sc-machine events</li>
 *     <li>Keynodes finder and resolver</li>
 *     <li>Method for creating sc-link with binary content (and get/set methods)</li>
 *
 * </ul>
 *
 * @author artrayme
 * @since 0.0.1
 */
public interface ScMemory {

    /**
     * Method to create nodes with specified type in sc-machine.
     * You should pass a stream of node types that you want to create.
     *
     * @param elements - stream of node types.
     * @return stream of created nodes.
     */
    Stream<? extends ScNode> createNodes(Stream<NodeType> elements) throws ScMemoryException;


    /**
     * Method to create edges with specified type in sc-machine.
     * You should pass a stream of edge types that you want to create.
     * Also, you should pass a stream of source sc-elements and a stream of target sc-elements.
     * Edges will be created between the corresponding source and target with corresponding type
     * All passed streams must have the same length.
     *
     * @param elements - stream of edge types.
     * @param sources  - stream of source nodes.
     * @param targets  - stream of target nodes.
     * @return stream of created edges.
     */
    Stream<? extends ScEdge> createEdges(Stream<EdgeType> elements,
                                         Stream<? extends ScElement> sources,
                                         Stream<? extends ScElement> targets) throws ScMemoryException;

    /**
     * Method to create sc-link with specified type and integer content in sc-machine.
     * You should pass a stream of sc-link types that you want to create.
     * Also, you should pass a stream of integer content.
     * Sc-link with corresponding type and content will be created.
     * All passed streams must have the same length.
     *
     * @return stream of created integer sc-links
     */
    Stream<? extends ScLinkInteger> createIntegerLinks(Stream<LinkType> elements, Stream<Integer> content) throws ScMemoryException;

    /**
     * Method to create sc-link with specified type and float content in sc-machine.
     * You should pass a stream of sc-link types that you want to create.
     * Also, you should pass a stream of float content.
     * Sc-link with corresponding type and content will be created.
     * All passed streams must have the same length.
     *
     * @return stream of created float sc-links
     */
    Stream<? extends ScLinkFloat> createFloatLinks(Stream<LinkType> elements, Stream<Float> content) throws ScMemoryException;

    /**
     * Method to create sc-link with specified type and string content in sc-machine.
     * You should pass a stream of sc-link types that you want to create.
     * Also, you should pass a stream of string content.
     * Sc-link with corresponding type and content will be created.
     * All passed streams must have the same length.
     *
     * @return stream of created string sc-links
     */
    Stream<? extends ScLinkString> createStringLinks(Stream<LinkType> elements, Stream<String> content) throws ScMemoryException;

    /**
     * Method to remove any sc-element in sc-machine.
     * You should pass a stream of sc-elements that you want to remove.
     *
     * @return the status of the request
     */
    boolean deleteElements(Stream<? extends ScElement> elements) throws ScMemoryException;

    /**
     * Method to search for sc-constructions by pattern Node-Edge-Node.
     * This pattern is a variation of the F_A_A pattern.
     * You must pass one particular sc-node,
     * the type of the edge you are looking for,
     * and the type of the node you are looking for.
     *
     * @return stream of edges (source of each edge will have address of fixed node)
     */
    Stream<? extends ScEdge> findByTemplateNodeEdgeNode(ScNode fixedNode, EdgeType edgeType, NodeType nodeType) throws ScMemoryException;

    /**
     * Method for searching all sc-constructions by the pattern Node-Edge-Link.
     * This pattern is a variation of the F_A_A pattern.
     * You must pass one particular sc-node,
     * the type of the edge you are looking for,
     * and the type of the sc-link you are looking for.
     *
     * @param fixedNode   - known node.
     * @param edgeType    - type of unknown edge (must be variable)
     * @param linkType    - type of unknown sc-link (must be variable)
     * @param contentType - content type of unknown sc-link.
     * @return stream of edges (source of each edge will have address of fixed node)
     * @since 0.3.0
     */
    Stream<? extends ScEdge> findByTemplateNodeEdgeLink(ScNode fixedNode,
                                                        EdgeType edgeType,
                                                        LinkType linkType,
                                                        LinkContentType contentType) throws ScMemoryException;

    /**
     * Method for searching all sc-constructions by the pattern Node-Edge-Link with relation.
     * This pattern is a variation of the F_A_A_F_A pattern.
     * You must pass one particular sc-node,
     * the type of the edge you are looking for,
     * and the type of the sc-link you are looking for.
     * Also, you must pass node of relation and type of relation sc-edge
     *
     *              (fixedRelationNode)
     *                      |
     *                      | relationEdgeType
     *                      |
     * (fixedNode)-----------------------(linkType)
     *                   edgeType
     *
     * @param fixedNode   - known node.
     * @param edgeType    - type of unknown edge (must be variable)
     * @param linkType    - type of unknown sc-link (must be variable)
     * @param contentType - content type of unknown sc-link.
     * @param fixedRelationNode - known relation node
     * @param relationEdgeType - type of unknown relation sc-link (must be variable)
     *
     * @return stream of edges (source of each edge will have address of fixed node)
     * @since 0.3.0
     */
    Stream<? extends ScEdge> findByTemplateNodeEdgeLinkWithRelation(ScNode fixedNode,
                                                                    EdgeType edgeType,
                                                                    LinkType linkType,
                                                                    LinkContentType contentType,
                                                                    ScNode fixedRelationNode,
                                                                    EdgeType relationEdgeType) throws ScMemoryException;

    Stream<Boolean> setIntegerLinkContent(Stream<? extends ScLinkInteger> links, Stream<Integer> content) throws ScMemoryException;

    Stream<Boolean> setFloatLinkContent(Stream<? extends ScLinkFloat> links, Stream<Float> content) throws ScMemoryException;

    Stream<Boolean> setStringLinkContent(Stream<? extends ScLinkString> links, Stream<String> content) throws ScMemoryException;

    Stream<Integer> getIntegerLinkContent(Stream<? extends ScLinkInteger> elements) throws ScMemoryException;

    Stream<Float> getFloatLinkContent(Stream<? extends ScLinkFloat> elements) throws ScMemoryException;

    Stream<String> getStringLinkContent(Stream<? extends ScLinkString> elements) throws ScMemoryException;


    /**
     * Implementation specific!
     * <p></p>
     * It is recommended to open ScMemory before use.
     *
     * @since 0.3.0
     */
    void open() throws Exception;

    /**
     * Implementation specific!
     * <p></p>
     * You must close ScMemory if you have opened it before using the method ({@link #open()})
     *
     * @since 0.3.0
     */
    void close() throws Exception;
}
