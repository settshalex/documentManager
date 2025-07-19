import React, { useCallback } from 'react';
import { useDropzone } from 'react-dropzone';
import { Box, Text, Center } from '@chakra-ui/react';

const DragDropUpload = ({ onFileSelect }) => {
    const onDrop = useCallback(acceptedFiles => {
        onFileSelect(acceptedFiles);
    }, [onFileSelect]);

    const { getRootProps, getInputProps, isDragActive } = useDropzone({ onDrop });

    return (
        <Center
            {...getRootProps()}
            p={10}
            border="2px dashed"
            borderColor={isDragActive ? "blue.400" : "gray.200"}
            borderRadius="md"
            bg={isDragActive ? "blue.50" : "gray.50"}
            cursor="pointer"
            transition="all 0.2s"
            _hover={{
                borderColor: "blue.400",
                bg: "blue.50"
            }}
        >
            <input {...getInputProps()} />
            <Text color="gray.500">
                {isDragActive
                    ? "Drop the files here..."
                    : "Drag 'n' drop files here, or click to select files"}
            </Text>
        </Center>
    );
};

export default DragDropUpload;
