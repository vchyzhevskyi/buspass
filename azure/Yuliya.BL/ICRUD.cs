namespace Yuliya.BL
{
    public interface ICRUD<T, in TK>
    {
        void Create(T entity);
        T Read(TK key);
        void Update(T entity);
        void Delete(T entity);
    }
}