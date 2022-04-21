package com.obstrom.packerservice.deserializer;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.obstrom.packerservice.StandardUnitsUtil;
import com.obstrom.packerservice.dto.ContainerRequestDto;
import com.obstrom.packerservice.dto.ItemRequestDto;
import com.obstrom.packerservice.dto.PackingJobRequestDto;
import com.obstrom.packerservice.exception.EnumFieldException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PackingJobRequestDtoDeserializer extends JsonDeserializer<PackingJobRequestDto> {

    @Override
    public PackingJobRequestDto deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        JsonNode node = p.getCodec().readTree(p);

        StandardUnitsUtil.Length length =
                parseUnit(StandardUnitsUtil.Length.class, node, "lengthUnitType");

        StandardUnitsUtil.Weight weight =
                parseUnit(StandardUnitsUtil.Weight.class, node, "weightUnitType");


        ObjectMapper objectMapper = new ObjectMapper();

        // TODO - Boxes list
        List<ContainerRequestDto> boxes;
        JsonNode boxesNode = node.get("boxes");
        if (boxesNode == null) {
            boxes = null;
        } else {
            boxes = new ArrayList<>();
            for (JsonNode n : boxesNode) {
                boxes.add(objectMapper.treeToValue(n, ContainerRequestDto.class));
            }
        }

        // TODO - Products list
        List<ItemRequestDto> products;
        JsonNode productNode = node.get("products");
        if (productNode == null) {
            products = null;
        } else {
            products = new ArrayList<>();
            for (JsonNode n : productNode) {
                products.add(objectMapper.treeToValue(n, ItemRequestDto.class));
            }
        }

        return new PackingJobRequestDto(length, weight, boxes, products);
    }

    private <E extends Enum<E>> E parseUnit(Class<E> type, JsonNode mainNode, String key) {
        try {
            JsonNode node = mainNode.get(key);
            if (node == null) throw new EnumFieldException(String.format("Missing '%s'", key));
            return E.valueOf(type, node.asText().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new EnumFieldException(String.format("Could not parse '%s'", key), e.getCause());
        }
    }

}
