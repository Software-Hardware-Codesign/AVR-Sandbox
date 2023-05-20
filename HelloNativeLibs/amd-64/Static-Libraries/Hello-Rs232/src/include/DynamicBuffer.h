/**
 * @file DynamicBuffer.h
 * @author pavl_g.
 * @brief Represents a cross platform dynamic buffer wrapper.
 * @version 0.1
 * @date 2022-08-24
 * 
 * @copyright 
 * BSD 3-Clause License
 *
 * Copyright (c) 2022, Scrappers Team, The AVR-Sandbox Project, Serial4j API.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 *
 * 3. Neither the name of the copyright holder nor the names of its
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
#ifndef _DYNAMIC_BUFFER
#define _DYNAMIC_BUFFER 

#include<stdlib.h>
#include<ErrnoUtils.h>
#include<BufferUtils.h>

struct DynamicBuffer {

    int count = 0;
    int isProcessed = 0;

    /**
     * Declares and initializes a pointer that points to 
     * other void* buffers starting from index zero. 
     * 
     * @note The pointer is of single size of a type.
     * @note The pointer points to only and only one buffer at a time.
     * @note New buffers can be added to this pointer by dereferencing it and adding one to the memory address to move 
     * it to a new cell block.
     * e.g: 
     * 1) First way of adding a new buffer to this pointer using the deep copy:
     *     buffer[index] = (void*) calloc(1, sizeof(void*));
     *     buffer[index] = item;
     * 
     * 2) Second way of adding a new buffer to this pointer (the one used here):
     *     *(buffer += count) = (void*) calloc(1, sizeof(void*));
     *     *buffer = item;
     *     buffer -= count;
     * 
     * 3) The superficial copy example:
     *     buffer[index] = item;
     */
    void** buffer = (void**) calloc(1, sizeof(void**));;

    static inline struct DynamicBuffer* createNewInstance() {
        return (struct DynamicBuffer*) calloc(1, sizeof(struct DynamicBuffer));
    }

    /**
     * Retrieves the pointer to this dynamic buffer.
     * 
     * @return a pointer to this array of buffers.
     */
    void** getBuffer() {
        return buffer;
    }

    /**
     * Retrieves this structure size.
     * 
     * @return an integer representing this struct in bytes.
     */
    size_t getBufferSize() {
        return sizeof(struct DynamicBuffer);
    }

    /**
     * Resets the pointer value back to zero.
     */
    void resetDataPointer() {
        this->count = 0;
    }

    /**
     * Gets the memory address to the integer of the items count.
     * 
     * @return a pointer referring to the memory address of the integer that represents the item counts.
     */
    int* getItemsCount();

    /**
     * Adds a new buffer to this pointer in a new index.
     * 
     * @param item a void* buffer to add.
     */
    void add(void* item);

    /**
     * Adds a new buffer on a particular location in this pointer replacing an old one if exists.
     * 
     * @param index the index where the new buffer will be located in this pointer.
     * @param item the buffer to add.
     */
    void add(int index, void* item);

    /**
     * Frees a buffer from the memory at a particular index.
     * @warning this method call is expensive as it removes and revalidates the whole buffer from NULL pointers.
     * 
     * @param index the index of the buffer to remove.
     */
    void removeAt(int index);

    /**
     * Frees all the buffers of this pointer from the memory.
     */
    void removeAll();

    /**
     * Retrieves a buffer index.
     * 
     * @param item the buffer to get its index.
     * @return the buffer index in an integer format.
     */
    int getItemIndex(void* item);

    /**
     * Retrieves a buffer from this pointer using its index.
     * 
     * @param index the index where the buffer is located in this pointer.
     * @return the buffer corresponding to this index.
     */
    void* getItem(int index);
};


#endif
