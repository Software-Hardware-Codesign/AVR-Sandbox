#include<DynamicBuffer.h>

void DynamicBuffer::add(void* item) {
  
    /* move the pointer to point to the last item */
    /* then, obtain a superficial copy */
    void** copy = (buffer += count);
    /* dereference and evalute using the superficial copy */
    *copy = (void*) calloc(1, sizeof(void*));
    *copy = item;
    /* move the pointer back to the start of the buffer (first item) */
    buffer -= count;
    count++;
} 

void DynamicBuffer::add(int index, void* item) {
    /* adds on the count if the location was empty previously */
    if (buffer[index] == NULL) {
        ++count;
    }
    buffer[index] = item;
}

void DynamicBuffer::removeAt(int index) {
    BufferUtils::nullifyBuffer(buffer, index);
    BufferUtils::reValidateBuffer(buffer, getItemsCount(), &(this->isProcessed));
    
    while (!this->isProcessed);
    this->isProcessed = 0;
    
    count--;
}

void DynamicBuffer::removeAll() {
    for (int i = 0; i < *(this->getItemsCount()); i++) {
        BufferUtils::nullifyBuffer(buffer, i);
    }

    BufferUtils::reValidateBuffer(buffer, getItemsCount(), &(this->isProcessed));
    
    while (!this->isProcessed);
    this->isProcessed = 0;

    this->resetDataPointer();
}

void* DynamicBuffer::getItem(int index) {
    return buffer[index];
}

int DynamicBuffer::getItemIndex(void* item) {
    for (int i = 0; i < *getItemsCount(); i++) {
        if (buffer[i] == item) {
            return i;
        }
    }
    return ERR_OPERATION_FAILED;
}

int* DynamicBuffer::getItemsCount() {
    return &count;
}
