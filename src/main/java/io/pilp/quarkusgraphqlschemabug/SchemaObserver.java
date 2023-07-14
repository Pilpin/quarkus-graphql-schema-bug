package io.pilp.quarkusgraphqlschemabug;

import graphql.schema.*;
import graphql.util.TraversalControl;
import graphql.util.TraverserContext;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;

import static graphql.Scalars.GraphQLString;

@ApplicationScoped
public class SchemaObserver {
    public GraphQLSchema.Builder beforeSchemaBuild(@Observes GraphQLSchema.Builder builder) {
        GraphQLSchema _schema = SchemaTransformer.transformSchema(builder.build(), new GraphQLTypeVisitorStub() {
            @Override
            public TraversalControl visitGraphQLObjectType(
                    GraphQLObjectType node,
                    TraverserContext<GraphQLSchemaElement> context
            ) {
                if (node.getName().equals("Country")) {
                    System.out.printf("Before edit: %s\n", node);

                    return changeNode(context, node.transform(builder -> builder.field(
                            GraphQLFieldDefinition.newFieldDefinition()
                                    .name("code")
                                    .type(GraphQLString)
                                    .build())));
                }

                return super.visitGraphQLObjectType(node, context);
            }
        });
        System.out.printf("After edit: %s\n", _schema.getObjectType("Country"));

        return GraphQLSchema.newSchema(_schema);
    }
}
