import React from 'react';
import {
    Box,
    Text,
    List,
    ListItem,
    Icon,
} from '@chakra-ui/react';
import { AttachmentIcon } from '@chakra-ui/icons';

const FilePreview = ({ files }) => {
    return (
        <Box mt={4}>
            <Text fontSize="sm" fontWeight="medium" mb={2}>
                Selected Files ({files.length}):
            </Text>
            <List spacing={2}>
                {files.map((file, index) => (
                    <ListItem key={index} display="flex" alignItems="center">
                        <Icon as={AttachmentIcon} mr={2} />
                        <Text fontSize="sm">
                            {file.name} ({(file.size / 1024 / 1024).toFixed(2)} MB)
                        </Text>
                    </ListItem>
                ))}
            </List>
        </Box>
    );
};

export default FilePreview;
