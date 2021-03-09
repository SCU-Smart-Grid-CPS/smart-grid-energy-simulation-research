/*
 * Copyright QTronic GmbH. All rights reserved.
 */

/* -------------------------------------------------------------------------
 * stack.c
 * A stack of pointers.
 * Author: Jakob Mauss, January 2010.
 * -------------------------------------------------------------------------*/

#ifndef STACK_H
#define STACK_H
#ifdef __cplusplus
extern "C" {
#endif

typedef struct {
    void** stack;
    int stackSize;    // allocated size of stack
    int stackPos;     // array index of top element, -1 if stack is empty.
    int initialSize;  // how many element to allocate initially
    int inc;          // how many elements to allocate when stack gets full
} Stack;

Stack* stackNew(int initialSize, int inc);
int stackIsEmpty(Stack* s);
int stackPush(Stack* s, void* e);
void* stackPeek(Stack* s);
void* stackPop(Stack* s);
void** stackPopAllAsArray(Stack* s, int *size);
void** stackLastPopedAsArray0(Stack* s, int n);
void stackFree(Stack* s);

#ifdef __cplusplus
} // closing brace for extern "C"
#endif
#endif // STACK_H